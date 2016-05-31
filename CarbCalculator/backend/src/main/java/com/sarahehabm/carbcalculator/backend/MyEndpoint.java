/*
   For step-by-step instructions on connecting your Android application to this backend module,
   see "App Engine Java Endpoints Module" template documentation at
   https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

package com.sarahehabm.carbcalculator.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.inject.Named;

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

    /** A simple endpoint method that takes a name and says Hi back */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) {
        MyBean response = new MyBean();
        response.setData("Hi, " + name);

        return response;
    }

    /** A simple endpoint method that retrieves all the food/beverage items */
    @ApiMethod(name = "getItems")
    public MyBean getItems() {

        /*String content = null;
        File file = new File("/src/assets/items.txt");
        FileReader fileReader = null;

        try {
            fileReader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            fileReader.read(chars);
            content = new String(chars);
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            content = "FileNotFoundException";
        } catch (IOException e) {
            e.printStackTrace();
            content = "IOException";
        } finally {
            if(fileReader!=null)
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    content = "finally; IOException";
                }
        }*/

        String str = "", content = "";
        StringBuffer buf = new StringBuffer();

        String error = "";
        Class clazz = this.getClass();
        ClassLoader classLoader;
        InputStream inputStream;
        if(clazz == null)
            error = "getClass() was NULL";
        else {
            classLoader = clazz.getClassLoader();

            if (classLoader == null)
                error = "classLoader was NULL";
            else {
//                inputStream = classLoader.getResourceAsStream("items.txt");
//                inputStream = classLoader.getResourceAsStream("items.txt");
//                inputStream = getClass().getResourceAsStream("items.txt");
                inputStream = getClass().getResourceAsStream(
                        "/main/java/com/sarahehabm/carbcalculator/backend/items.txt");

                if (inputStream == null)
                    error = "inputStream was NULL";
                else {
                    try {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        if (inputStream != null) {
                            if(reader == null)
                                content = "reader was NULL";
                            else {
                                while ((str = reader.readLine()) != null) {
                                    buf.append(str + "\n");
                                }
                                if (buf != null)
                                    content = buf.toString();
                                else
                                    content = "buf was NULL";
                            }
                        } else {
                            content = "inputStream was NULL";
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        content = "IOException";
                    } finally {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                            content = "finally; IOException";
                        }
                    }
                }
            }
        }

//        String content2 = "{\"items\":[{\"id\":0,\"name\":\"Item0\",\"isFavorite\":false},{\"id\":1,\"name\":\"Item1\",\"isFavorite\":false},{\"id\":2,\"name\":\"Item2\",\"isFavorite\":false},{\"id\":3,\"name\":\"Item3\",\"isFavorite\":false},{\"id\":4,\"name\":\"Item4\",\"isFavorite\":false},{\"id\":5,\"name\":\"Item5\",\"isFavorite\":false},{\"id\":6,\"name\":\"Item6\",\"isFavorite\":false},{\"id\":7,\"name\":\"Item7\",\"isFavorite\":false},{\"id\":8,\"name\":\"Item8\",\"isFavorite\":false},{\"id\":9,\"name\":\"Item9\",\"isFavorite\":false}]}";
//        String content2 = "[{\"id\":0,\"name\":\"Item0\",\"isFavorite\":false},{\"id\":1,\"name\":\"Item1\",\"isFavorite\":false},{\"id\":2,\"name\":\"Item2\",\"isFavorite\":false},{\"id\":3,\"name\":\"Item3\",\"isFavorite\":false},{\"id\":4,\"name\":\"Item4\",\"isFavorite\":false},{\"id\":5,\"name\":\"Item5\",\"isFavorite\":false},{\"id\":6,\"name\":\"Item6\",\"isFavorite\":false},{\"id\":7,\"name\":\"Item7\",\"isFavorite\":false},{\"id\":8,\"name\":\"Item8\",\"isFavorite\":false},{\"id\":9,\"name\":\"Item9\",\"isFavorite\":false}]";
        String content2 = "[{\"id\":1,\"name\":\"Apple\",\"isFavorite\":false},{\"id\":2,\"name\":\"Yoghurt\",\"isFavorite\":false},{\"id\":3,\"name\":\"Rice\",\"isFavorite\":false},{\"id\":4,\"name\":\"Vegetables\",\"isFavorite\":false},{\"id\":5,\"name\":\"Salad\",\"isFavorite\":false}]";

        System.out.println("CONTENT= " + content);
        System.out.println("CONTENT2= " + content2);

        MyBean response = new MyBean();
//        response.setData("ERROR: " + error + "      Items: " + content + "      Items (CONTENT2): " + content2);
        response.setData(content2);

        //TODO read data from file instead of hardcoded string

        return response;
    }


    /** A simple endpoint method that retrieves all the food/beverage items's amounts */
    @ApiMethod(name = "getAmounts")
    public MyBeanAmount getAmounts() {
        String content = "[{\"id\":1,\"carbGrams\":10,\"quantity\":100,\"unit\":\"gram\",\"itemId\":2},{\"id\":2,\"carbGrams\":15,\"quantity\":2,\"unit\":\"tablespoon\",\"itemId\":3},{\"id\":3,\"carbGrams\":14,\"quantity\":100,\"unit\":\"gram\",\"itemId\":1},{\"id\":4,\"carbGrams\":45,\"quantity\":1,\"unit\":\"cup\",\"itemId\":3},{\"id\":5,\"carbGrams\":28,\"quantity\":100,\"unit\":\"gram\",\"itemId\":3},{\"id\":6,\"carbGrams\":15,\"quantity\":2,\"unit\":\"tablespoon\",\"itemId\":4},{\"id\":7,\"carbGrams\":10,\"quantity\":5,\"unit\":\"tablespoon\",\"itemId\":5}]";

        MyBeanAmount response = new MyBeanAmount();
        response.setData(content);

        return response;
    }
}
