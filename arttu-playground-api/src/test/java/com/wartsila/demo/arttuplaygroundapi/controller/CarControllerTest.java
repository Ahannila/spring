package com.wartsila.demo.arttuplaygroundapi.controller;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.bind.BinderConfiguration;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.wartsila.demo.arttuplaygroundapi.SpringServiceBean;
import com.wartsila.demo.arttuplaygroundapi.model.dto.CarDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.CarFullDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.OwnerDto;
import com.wartsila.demo.arttuplaygroundapi.util.PostgresBinderConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static com.wartsila.demo.arttuplaygroundapi.Utils.TBL_CAR;
import static com.wartsila.demo.arttuplaygroundapi.Utils.TBL_OWNER;
import static com.wartsila.demo.arttuplaygroundapi.util.DBOperations.CLEAN_DB;
import static com.wartsila.demo.arttuplaygroundapi.util.UtilsTestEnv.tblCar;
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

/**
 * Created by Giulio De Biasio (GBI012)
 * on 22.6.2023
 * This is the test class for the CarController. The test env is a particular SpringBoot application, and for this reason
 * all the settings can be done in a dedicate setting file (application-test.properties).
 * In this way, you can specify a separate database to use for the tests: this is fundamental, in order to not touch
 * the real database.
 * Flyway is reused also in test classes: this brings to have a database structure same as in production.
 */

@EnableWebMvc
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class CarControllerTest extends SpringServiceBean {

    private final String CAR_ENDPOINT = "/api/car";
    private final BinderConfiguration binderConfiguration = new PostgresBinderConfiguration();

    /**
     * This block is executed BEFORE each test. Digging into how is doing, you'll see that the
     * database is cleaned everytime, and some data are inserted.
     * Having this running BEFORE each test, result on having a well known structure and a clean scenario at every test.
     */
    @BeforeEach
    public void cleanDb() {
        new DbSetup(new DataSourceDestination(dataSource), CLEAN_DB, binderConfiguration).launch();
    }

    @Test
    public void whenPostEndpointCalledReturn202AndEntityCreated() throws Exception {
        String brandName = "Porsche";
        CarDto carToCreate = new CarDto();
        carToCreate.setBrand(brandName);
        carToCreate.setYear(2023);

        mockMvc.perform(post(CAR_ENDPOINT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carToCreate)))
                .andExpect(status().is(202));

        assertThat(countRowsInTable(jdbcTemplate, tblCar()), equalTo(4));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                String.format("id='%d' and brand='%s'", 100L, brandName)
        ), equalTo(1));
    }

    @Test
    public void whenPostEndpointCalledWithOwnerExistingReturn202AndEntityCreated() throws Exception {
        String brandName = "Porsche";
        CarFullDto carToCreate = new CarFullDto();
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(1L);
        carToCreate.setBrand(brandName);
        carToCreate.setYear(2023);
        carToCreate.setOwner(ownerDto);

        mockMvc.perform(post(CAR_ENDPOINT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carToCreate)))
                .andExpect(status().is(202));

        assertThat(countRowsInTable(jdbcTemplate, tblCar()), equalTo(4));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                String.format("id='%d' and brand='%s' and owner='%d'", 100L, brandName, ownerDto.getId())
        ), equalTo(1));
    }

    @Test
    public void whenPostEndpointCalledWithOwnerNOTExistingReturn400() throws Exception {
        String brandName = "Porsche";
        CarFullDto carToCreate = new CarFullDto();

        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(100L);

        carToCreate.setBrand(brandName);
        carToCreate.setYear(2023);
        carToCreate.setOwner(ownerDto);

        mockMvc.perform(post(CAR_ENDPOINT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carToCreate)))
                .andExpect(status().is(400));

        assertThat(countRowsInTable(jdbcTemplate, tblCar()), equalTo(3));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                String.format("id='%d' and brand='%s' and owner='%d'", 100L, brandName, ownerDto.getId())
        ), equalTo(0));
    }

    public void whenCreateCarWithYearNotValidThenReturn400() {}
        //This is done on NoCarEntityWithWrongData which now renamed to
    @Test
    void deleteCarWorksAndReturns202() throws Exception{
        int carIdToDelete = 1;
        mockMvc.perform(delete(CAR_ENDPOINT + "/" + carIdToDelete))
                .andExpect(status().is(202));
        assertThat(countRowsInTable(jdbcTemplate, tblCar()), equalTo(2));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                String.format("id='%d'", 1L)
        ), equalTo(0));

    }

    /**
     * In the controller, is written that in case of Exception, 500 is returned. I'm expecting to have this in case
     * the entity I want to remove is not present in the database.
     * Having the @Before block executed, means I've entities  with id 1, 2 and 3 created.
     * I try here to delete the 100, which is not existing.
     */

    @Test
    void whenDeleteEntityNotExistingThen404() throws Exception {
        int carIdToDelete = 100;
        mockMvc.perform(delete(CAR_ENDPOINT + carIdToDelete))
                .andExpect(status().is(404));
    }

    @Test
    public void createCarWithNoValidDataReturns400() throws Exception {
        String brandname = "";
        CarDto carToTest = new CarDto();
        carToTest.setBrand(brandname);
        carToTest.setYear(-500);

        mockMvc.perform(post(CAR_ENDPOINT ).
                contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carToTest)))
                .andExpect(status().is(400));

    }

    /**
     * Maybe using a mockList to assertThatEquals to the real list
     *
    @Test
    public void getCarListReturnsListWithRightDataAnd202() throws Exception {
        mockMvc.perform(get(CAR_ENDPOINT + "/get-list"))
                .andExpect(status().is(202))
                .andExpect(jsonPath("$[0].brand", is("Ferrari")));
    }*/

    @Test
    void getCarIdWorksAndReturns202() throws Exception {
        int carIdToGet = 2;
        mockMvc.perform(get(CAR_ENDPOINT + "/"+ carIdToGet))
                .andExpect(status().is(202))
                .andExpect(jsonPath("$.*", hasSize(4)));
    }

    @Test
    void getCarByWrongIdReturns404() throws Exception {
        int carIdToGet = 100;
        mockMvc.perform(get(CAR_ENDPOINT + carIdToGet))
                .andExpect(status().is(404));
    }


    @Test
    void updateCarByIdReturns202() throws Exception {
        String brandName = "Porsche";
        CarDto carToCreate = new CarDto();
        carToCreate.setBrand(brandName);
        carToCreate.setYear(2023);

        int id = 1;
        mockMvc.perform(put(CAR_ENDPOINT + "/" +  id  )
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(carToCreate)))
                .andExpect(status().is(202));

        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                String.format("id='%d' and brand='%s'", 1, brandName)
        ), equalTo(1));
    }

    @Test
    public void updateCarFailAndThrowsResourceNotFound() throws Exception {
        int carToUpdate = 100;

        mockMvc.perform(put(CAR_ENDPOINT + "/" +  carToUpdate ))
                .andExpect(status().is(400));

    }
    @Test
    public void updateCarOwnerThen200() throws Exception {
        int carToupdate = 1;
        int ownerToAdd = 0;

        mockMvc.perform(put(CAR_ENDPOINT + "/owner/" + carToupdate +"/"+ ownerToAdd))
                .andExpect(status().is(202));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                String.format("id='%d' and owner='%d'",1,0))
        ,equalTo(1));
    }

    @Test
    public void updateCarOwnerNotExistingThen404() throws Exception {
        int carToupdate = 1;
        int ownerToAdd = 1000;

        mockMvc.perform(put(CAR_ENDPOINT + "/owner/" + carToupdate +"/"+ ownerToAdd))
                .andExpect(status().is(404));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblCar(),
                        String.format("id='%d' and owner='%d'",1,1000))
                ,equalTo(0));
    }

    @Test
    public void whenGetListIsCalledReturnCarListAndNestedOwner() throws Exception {
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

        mockMvc.perform(get(CAR_ENDPOINT + "/get-list"))
                .andExpect(status().is(202))
                .andExpect(jsonPath("$", hasSize(6)))
                .andExpect(jsonPath("$[3].owner.id", is(100)))
                .andExpect(jsonPath("$[3].owner.firstname", is("Owner")));
    }

}
