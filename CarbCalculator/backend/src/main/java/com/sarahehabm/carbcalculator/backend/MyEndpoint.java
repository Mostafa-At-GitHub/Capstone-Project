/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.sarahehabm.carbcalculator.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

/** An endpoint class we are exposing */
@Api(
  name = "myApi",
  version = "v1",
  namespace = @ApiNamespace(
    ownerDomain = "backend.carbcalculator.sarahehabm.com",
    ownerName = "backend.carbcalculator.sarahehabm.com",
    packagePath=""
  )
)
public class MyEndpoint {

    /** A simple endpoint method that retrieves all the food/beverage items */
    @ApiMethod(name = "getItems")
    public MyBean getItems() {
        String content = "[{\"id\":1,\"name\":\"Apple\",\"isFavorite\":false}," +
                "{\"id\":2,\"name\":\"Yoghurt\",\"isFavorite\":false}," +
                "{\"id\":3,\"name\":\"Rice\",\"isFavorite\":false}," +
                "{\"id\":4,\"name\":\"Vegetables\",\"isFavorite\":false}," +
                "{\"id\":5,\"name\":\"Salad\",\"isFavorite\":false}," +
                "{\"id\":6,\"name\":\"Toast\",\"isFavorite\":false}," +
                "{\"id\":7,\"name\":\"Pasta\",\"isFavorite\":false}," +
                "{\"id\":8,\"name\":\"Milk\",\"isFavorite\":false}," +
                "{\"id\":9,\"name\":\"Popcoorn\",\"isFavorite\":false}," +
                "{\"id\":10,\"name\":\"Almonds\",\"isFavorite\":false}]";

        MyBean response = new MyBean();
        response.setData(content);

        return response;
    }


    /** A simple endpoint method that retrieves all the food/beverage items's amounts */
    @ApiMethod(name = "getAmounts")
    public MyBeanAmount getAmounts() {
        String content = "[{\"id\":1,\"carbGrams\":10,\"quantity\":100,\"unit\":\"gram\",\"itemId\":2}," +
                "{\"id\":2,\"carbGrams\":15,\"quantity\":2,\"unit\":\"tablespoon\",\"itemId\":3}," +
                "{\"id\":3,\"carbGrams\":14,\"quantity\":100,\"unit\":\"gram\",\"itemId\":1}," +
                "{\"id\":4,\"carbGrams\":45,\"quantity\":1,\"unit\":\"cup\",\"itemId\":3}," +
                "{\"id\":5,\"carbGrams\":28,\"quantity\":100,\"unit\":\"gram\",\"itemId\":3}," +
                "{\"id\":6,\"carbGrams\":15,\"quantity\":2,\"unit\":\"tablespoon\",\"itemId\":4}," +
                "{\"id\":7,\"carbGrams\":10,\"quantity\":5,\"unit\":\"tablespoon\",\"itemId\":5}," +
                "{\"id\":8,\"carbGrams\":15,\"quantity\":1,\"unit\":\"slice\",\"itemId\":6}," +
                "{\"id\":9,\"carbGrams\":35,\"quantity\":100,\"unit\":\"gram\",\"itemId\":6}," +
                "{\"id\":10,\"carbGrams\":15,\"quantity\":2,\"unit\":\"tablespoon\",\"itemId\":7}," +
                "{\"id\":11,\"carbGrams\":35,\"quantity\":100,\"unit\":\"gram\",\"itemId\":7}," +
                "{\"id\":12,\"carbGrams\":12,\"quantity\":1,\"unit\":\"cup\",\"itemId\":8}," +
                "{\"id\":13,\"carbGrams\":5,\"quantity\":100,\"unit\":\"gram\",\"itemId\":8}," +
                "{\"id\":14,\"carbGrams\":74,\"quantity\":100,\"unit\":\"gram\",\"itemId\":9}," +
                "{\"id\":15,\"carbGrams\":20,\"quantity\":1,\"unit\":\"cup\",\"itemId\":10}," +
                "{\"id\":16,\"carbGrams\":22,\"quantity\":100,\"unit\":\"gram\",\"itemId\":10}]";

        MyBeanAmount response = new MyBeanAmount();
        response.setData(content);

        return response;
    }
}
