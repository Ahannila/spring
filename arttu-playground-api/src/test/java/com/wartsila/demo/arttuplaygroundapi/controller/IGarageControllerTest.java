package com.wartsila.demo.arttuplaygroundapi.controller;

import com.wartsila.demo.arttuplaygroundapi.SpringServiceBean;

/**
 * Created by Giulio De Biasio (GBI012)
 * on 11.7.2023
 */
public interface IGarageControllerTest {
    void whenGetListGarageListReturned() throws Exception;
    void whenCreateGarageGarageCreated() throws Exception;
    void whenCreateGarageWithCarCreated() throws Exception;
    void whenCreateGarageWithBadDataExceptionRaised() throws Exception;
    void whenDeleteGarageGarageDeleted() throws Exception;
    void whenDeleteGarageNotExistingExceptionRaised() throws Exception;
    void whenUpdateGarageGarageUpdated() throws Exception;
    void whenUpdateGarageWithBadDataExceptionRaised() throws Exception;
    void whenOccupyGarageGarageOccupiedWithCar() throws Exception;
    void whenOccupyGarageNotExistingExceptionRaised() throws Exception;
    void whenOccupyGarageWithCarNotExistingExceptionRaised() throws Exception;
    void whenFreeUpGarageGarageOccupiedWithCar() throws Exception;
    void whenFreeUpGarageNotExistingExceptionRaised() throws Exception;
    void whenFreeUpGarageWithCarNotExistingExceptionRaised() throws Exception;
}
