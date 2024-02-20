package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.controllers.Controller;

import java.util.Map;

/**
 * MVC controller that provides all the information about a provided park service.
 */
public interface SingleParkServiceInfoController extends Controller {

    /**
     * Returns all the info about the park service with the provided name.
     * In this method the names of the attributes and their values are formatted for the view.
     * @param parkServiceName the name of the park service.
     * @return all the info about the park service with the provided name.
     */
    Map<String, String> getAllParkServiceInfo(@NonNull String parkServiceName);
}
