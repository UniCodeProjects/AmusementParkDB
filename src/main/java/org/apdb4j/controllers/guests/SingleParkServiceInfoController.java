package org.apdb4j.controllers.guests;

import lombok.NonNull;
import org.apdb4j.controllers.Controller;

import java.util.List;
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

    /**
     * Returns the description of the provided park service.
     * @param parkServiceName the park service.
     * @return the description of the provided park service.
     */
    String getParkServiceDescription(@NonNull String parkServiceName);

    /**
     * Returns all the paths of the photos of the park service with the provided name.
     * @param parkServiceName the name of the park service.
     * @return all the paths of the photos of the provided park service.
     */
    List<String> getPhotosPath(@NonNull String parkServiceName);
}
