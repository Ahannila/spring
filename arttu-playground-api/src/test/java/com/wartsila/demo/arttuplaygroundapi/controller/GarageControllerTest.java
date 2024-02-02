package com.wartsila.demo.arttuplaygroundapi.controller;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.bind.BinderConfiguration;
import com.ninja_squad.dbsetup.destination.DataSourceDestination;
import com.wartsila.demo.arttuplaygroundapi.SpringServiceBean;
import com.wartsila.demo.arttuplaygroundapi.model.dto.CarDto;
import com.wartsila.demo.arttuplaygroundapi.model.dto.GarageFullDto;
import com.wartsila.demo.arttuplaygroundapi.util.PostgresBinderConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static com.wartsila.demo.arttuplaygroundapi.Utils.*;
import static com.wartsila.demo.arttuplaygroundapi.util.DBOperations.CLEAN_DB;
import static com.wartsila.demo.arttuplaygroundapi.util.UtilsTestEnv.tblGarage;
import static com.ninja_squad.dbsetup.Operations.insertInto;
import static com.ninja_squad.dbsetup.Operations.sequenceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTable;
import static org.springframework.test.jdbc.JdbcTestUtils.countRowsInTableWhere;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableWebMvc
@AutoConfigureMockMvc
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class GarageControllerTest extends SpringServiceBean implements IGarageControllerTest  {


    private final String GARAGE_ENDPOINT = "/api/garage";
    private final String CAR_ENDPOINT = "/api/car";

    private final BinderConfiguration binderConfiguration = new PostgresBinderConfiguration();

    @BeforeEach
    public void cleanDb() {
        new DbSetup(new DataSourceDestination(dataSource), CLEAN_DB, binderConfiguration).launch();
    }
    @Override
    @Test
    public void whenGetListGarageListReturned() throws Exception {
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
                                .values(102, "Volvo", 2022, 100).build(),
                        insertInto(TBL_GARAGE)
                                .columns("id", "name", "location", "car")
                                .values(100, "Mikes garage", "Helsinki", 100)
                                .values(101, "Jonys garage", "Oulu", 102).build()
                )
        ).launch();

        mockMvc.perform(get(GARAGE_ENDPOINT + "/get-list"))
                .andExpect(status().is(202))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].id", is(100)))
                .andExpect(jsonPath("$[1].name", is("Mikes garage")));

    }

    @Override
    @Test
    public void whenCreateGarageGarageCreated() throws Exception {
        String garageName = "Jims garage";
        String location = "Albertinkatu 40";
        GarageFullDto garageToCreate = new GarageFullDto();
        garageToCreate.setLocation(location);
        garageToCreate.setName(garageName);


        mockMvc.perform(post(GARAGE_ENDPOINT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(garageToCreate)))
                .andExpect(status().is(202));

        assertThat(countRowsInTable(jdbcTemplate, tblGarage()), equalTo(2));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblGarage(),
                String.format("id='%d' and name='%s'", 100L, garageName)
        ), equalTo(1));
    }

    @Override
    @Test
    public void whenCreateGarageWithCarCreated() throws Exception {
        String garageName = "Jimmys garage";
        String location = "Albertinkatu 41";
        String brandName = "Porsche";
        CarDto carToCreate = new CarDto();
        carToCreate.setBrand(brandName);
        carToCreate.setYear(2023);

        GarageFullDto garageToCreate = new GarageFullDto();
        garageToCreate.setLocation(location);
        garageToCreate.setName(garageName);
        garageToCreate.setCar(carToCreate);


        mockMvc.perform(post(GARAGE_ENDPOINT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(garageToCreate)))
                .andExpect(status().is(202));

        assertThat(countRowsInTable(jdbcTemplate, tblGarage()), equalTo(2));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblGarage(),
                String.format("id='%d' and name='%s'", 100L, garageName)
        ), equalTo(1));
    }

    @Override
    @Test
    public void whenCreateGarageWithBadDataExceptionRaised() throws Exception{
        String garageName = "";
        String location = "";
        GarageFullDto garageToCreate = new GarageFullDto();
        garageToCreate.setLocation(location);
        garageToCreate.setName(garageName);

        mockMvc.perform(post(GARAGE_ENDPOINT )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(garageToCreate)))
                .andExpect(status().is(400));
    }

    @Override
    @Test
    public void whenDeleteGarageGarageDeleted() throws Exception {
        int garageToDelete = 0;
        mockMvc.perform(delete(GARAGE_ENDPOINT + "/" + garageToDelete))
                .andExpect(status().is(202));
        assertThat(countRowsInTable(jdbcTemplate, tblGarage()), equalTo(0));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblGarage(),
                String.format("id='%d'", 0L)
        ),equalTo(0));
    }

    @Override
    @Test
    public void whenDeleteGarageNotExistingExceptionRaised() throws Exception{
        int garageToDelete = 100;
        mockMvc.perform(delete(GARAGE_ENDPOINT + "/" + garageToDelete))
                .andExpect(status().is(404));


    }

    @Override
    @Test
    public void whenUpdateGarageGarageUpdated() throws Exception {
        String garageName = "Updated garage";
        String location = "Updated location";
        GarageFullDto garageToCreate = new GarageFullDto();
        garageToCreate.setLocation(location);
        garageToCreate.setName(garageName);

        int garageToUpdate = 0;

        mockMvc.perform(put(GARAGE_ENDPOINT+ "/" + garageToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(garageToCreate)))
                .andExpect(status().is(202));
        assertThat(countRowsInTable(jdbcTemplate, tblGarage()), equalTo(1));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblGarage(),
                String.format("id='%d' and name='%s'", 0L, garageName)
        ),equalTo(1));

    }

    @Override
    @Test
    public void whenUpdateGarageWithBadDataExceptionRaised() throws Exception {
        String garageName = "Updated garage";
        String location = "Updated location";
        GarageFullDto garageToCreate = new GarageFullDto();
        garageToCreate.setLocation(location);
        garageToCreate.setName(garageName);

        int garageToUpdate = 1000;

        mockMvc.perform(put(GARAGE_ENDPOINT+ "/" + garageToUpdate)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(garageToCreate)))
                .andExpect(status().is(404));

        assertThat(countRowsInTable(jdbcTemplate, tblGarage()), equalTo(1));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblGarage(),
                String.format("id='%d'", 1000)
        ),equalTo(0));

    }

    @Override
    @Test
    public void whenOccupyGarageGarageOccupiedWithCar() throws Exception {

        long garageId = 0L;
        long carId = 1L;

        mockMvc.perform(post(GARAGE_ENDPOINT + "/occupy/" + garageId + "/" +carId)
                ).andExpect(status().is(202));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblGarage(),
                String.format("id='%d' and car='%d'", 0,1)
        ),equalTo(1));
    }

    @Override
    @Test
    public void whenOccupyGarageNotExistingExceptionRaised() throws Exception {
        long garageId = 1342L;
        long carId = 1L;

        mockMvc.perform(post(GARAGE_ENDPOINT + "/occupy/" + garageId + "/" +carId)
        ).andExpect(status().is(404));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblGarage(),
                String.format("id='%d' and car='%d'", garageId,carId)
        ),equalTo(0));
    }

    @Override
    @Test
    public void whenOccupyGarageWithCarNotExistingExceptionRaised() throws Exception {
        long garageId = 0L;
        long carId = 1000L;

        mockMvc.perform(put(GARAGE_ENDPOINT + "/occupy/" + garageId + "/" +carId)
        ).andExpect(status().is(405));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblGarage(),
                String.format("id='%d' and car='%d'", garageId,carId)
        ),equalTo(0));
    }

    @Override
    @Test
    public void whenFreeUpGarageGarageOccupiedWithCar()  throws Exception{
        long garageId = 0L;

        mockMvc.perform(put(GARAGE_ENDPOINT + "/free-garage/" + garageId))
                .andExpect(status().is(202));
        assertThat(countRowsInTableWhere(jdbcTemplate, tblGarage(),
                String.format("id='%d' and car IS NULL", 0)
        ),equalTo(1));    }

    @Override
    @Test
    public void whenFreeUpGarageNotExistingExceptionRaised() throws Exception {
        long garageId = 444L;

        mockMvc.perform(put(GARAGE_ENDPOINT + "/free-garage/" + garageId))
                .andExpect(status().is(404));
    }

    @Override
    @Test
    public void whenFreeUpGarageWithCarNotExistingExceptionRaised() throws Exception{
        new DbSetup(new DataSourceDestination(dataSource),
                sequenceOf(
                        insertInto(TBL_GARAGE)
                                .columns("id", "name", "location")
                                .values(100, "Mikes garage", "Helsinki")
                                .values(101, "Jonys garage", "Oulu").build()
                )
        ).launch();

        mockMvc.perform(put(GARAGE_ENDPOINT + "/free-garage/" + 100))
                .andExpect(status().is(404));}

}
