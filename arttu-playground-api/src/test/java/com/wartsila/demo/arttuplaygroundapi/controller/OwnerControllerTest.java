package com.wartsila.demo.arttuplaygroundapi.controller;


import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.bind.BinderConfiguration;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.wartsila.demo.arttuplaygroundapi.SpringServiceBean;
import com.wartsila.demo.arttuplaygroundapi.model.dto.CarDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.OwnerDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.OwnerFullDto;
import com.wartsila.demo.arttuplaygroundapi.util.PostgresBinderConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.wartsila.demo.arttuplaygroundapi.Utils.TBL_CAR;
import static com.wartsila.demo.arttuplaygroundapi.Utils.TBL_OWNER;
import static com.wartsila.demo.arttuplaygroundapi.util.DBOperations.CLEAN_DB;
import static com.wartsila.demo.arttuplaygroundapi.util.UtilsTestEnv.tblCar;
import static com.wartsila.demo.arttuplaygroundapi.util.UtilsTestEnv.tblOwner;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class OwnerControllerTest  extends SpringServiceBean{

    private final String OWNER_ENDPOINT = "/api/owner";

    private final BinderConfiguration binderConfiguration = new PostgresBinderConfiguration();

    @BeforeEach
    public void cleanDb() {
        new DbSetup(new DataSourceDestination(dataSource), CLEAN_DB, binderConfiguration).launch();
    }


    @Test
    public void whenGetEndPointCalledToToReturnListReturn202() throws Exception {
        mockMvc.perform(get(OWNER_ENDPOINT + "/get-list"))
                .andExpect(status().is(202))
                .andExpect(jsonPath("$[0].firstname", is("Giulio")));
    }

    @Test
    public void whenPostEndPointCalledReturn202AndEntityCreated() throws Exception {
        String firstname = "Tomi";
        String lastname = "Petteri";
        OwnerDto owner = new OwnerDto();
        owner.setFirstname(firstname);
        owner.setLastname(lastname);

        mockMvc.perform(post(OWNER_ENDPOINT )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().is(202));
        assertThat(countRowsInTable(jdbcTemplate, tblOwner()), equalTo(4));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblOwner(),
                String.format("firstname='%s' and lastname='%s'", firstname,lastname)
        ), equalTo(1));

    }

    @Test
    public void whenPostEndpointIsCalledWithValidationFailedThen400() throws Exception {
        String firstname = "";
        String lastname = "1234";
        OwnerDto owner = new OwnerDto();
        owner.setFirstname(firstname);
        owner.setLastname(lastname);

        mockMvc.perform(post(OWNER_ENDPOINT)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().is(400));
    }

    @Test
    public void deleteOwnerWorksAndReturns200() throws Exception {
        int ownerId = 1;

        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                String.format("owner = '%d'", ownerId)
        ), equalTo(1));

        mockMvc.perform(delete(OWNER_ENDPOINT + "/" + ownerId))
                .andExpect(status().is(200));

        // Check that only the resource we wanted to deleted IS ACTUALLY DELETED
        assertThat(countRowsInTable(jdbcTemplate, tblOwner()), equalTo(2));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblOwner(),
                String.format("id = '%d'", ownerId)
        ), equalTo(0));

        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                String.format("owner is NULL")
        ), equalTo(1));
    }

    @Test
    public void deleteOwnerNotExistingReturns404() throws Exception {
        int ownerId = 100;

        mockMvc.perform(delete(OWNER_ENDPOINT + "/" + ownerId))
                .andExpect(status().is(404));
    }


    @Test
    public void putEndPointWorksByChangingOwnerDetails() throws Exception {
        String firstname = "Tomi";
        String lastname = "Petteri";
        OwnerDto owner = new OwnerDto();
        owner.setFirstname(firstname);
        owner.setLastname(lastname);

        mockMvc.perform(put(OWNER_ENDPOINT +"/"+ 0)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().is(200));
        assertThat(countRowsInTable(jdbcTemplate, tblOwner()),equalTo(3));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblOwner(),
                String.format("firstname='%s' and lastname='%s'",firstname,lastname)
        ), equalTo(1));
    }

    @Test
    public void whenPostEndpointCalledWithListOfCarsNOTExistingReturn400() throws Exception {
        String brandName1 = "Porsche";
        CarDto carToCreate1 = new CarDto();
        carToCreate1.setBrand(brandName1);
        carToCreate1.setYear(2023);

        String brandName2 = "Lexus";
        CarDto carToCreate2 = new CarDto();
        carToCreate2.setBrand(brandName2);
        carToCreate2.setYear(2023);



        ArrayList<CarDto> carList = new ArrayList<>();
        carList.add(carToCreate1);
        carList.add(carToCreate1);

        String firstname = "Tomi";
        String lastname = "Petteri";
        OwnerFullDto owner = new OwnerFullDto();
        owner.setFirstname(firstname);
        owner.setLastname(lastname);

        owner.setCars(carList);

        mockMvc.perform(post(OWNER_ENDPOINT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(owner)))
                .andExpect(status().is(400));

        /**
        assertThat(countRowsInTable(jdbcTemplate, tblCar()), equalTo(3));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                String.format("id='%d' and brand='%s' and owner='%d'", 100L, brandName, ownerDto.getId())
        ), equalTo(0));
    */
     }

    @Test
    public void whenGetListIsCalledReturnOwnerListAndNestedCar() throws Exception {
        // For this test, I update the database in order to have some cars owned by an owner.
        new DbSetup(new DataSourceDestination(dataSource),
                sequenceOf(
                        insertInto(TBL_OWNER)
                                .columns("id", "firstname", "lastname")
                                .values(100, "Owner", "With A Lot Of Cars")
                                .build(),
                        insertInto(TBL_CAR)
                                .columns("id", "brand", "year", "owner")
                                .values(100, "Lotus", 2023, 100)
                                .values(101, "Maserati", 2022, 100)
                                .values(102, "Volvo", 2022, 100).build()
                )
        ).launch();

        mockMvc.perform(get(OWNER_ENDPOINT + "/get-list"))
                .andExpect(status().is(202))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[3].firstname", is("Owner")))
                .andExpect(jsonPath("$[3].cars", hasSize(3)))
                .andExpect(jsonPath("$[3].cars[0].brand", is("Lotus")));
    }
}
