package at.fhtw.mtcg.controller.packages;

import at.fhtw.httpserver.server.HeaderMap;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class PackageControllerTest {
    @Test
    void testPackageControllerCreateCardPackage() throws Exception {
        Request request = new Request();
        Response response;

        //empty response = empty requestbody
        response = new PackageController().createCardPackage(request);
        assertTrue(response.get().contains("500 Internal Server Error"));

        //no token
        request.setBody("test");
        response = new PackageController().createCardPackage(request);
        assertTrue(response.get().contains("401 Unauthorized"));

        //user not admin
        HeaderMap headerMap = new HeaderMap();
        headerMap.ingest("Authorization: test-mtcgToken");
        request.setHeaderMap(headerMap);
        response = new PackageController().createCardPackage(request);
        assertTrue(response.get().contains("403 Forbidden"));

        //card already exists
        headerMap = new HeaderMap();
        headerMap.ingest("Authorization: admin-mtcgToken");
        request.setHeaderMap(headerMap);
        request.setBody("[{\"Id\":\"1\",\"Name\":\"Dragon\",\"Damage\":10.0}]");
        response = new PackageController().createCardPackage(request);
        assertTrue(response.get().contains("409 Conflict"));

        //create package
        request.setBody("[{\"Id\":\"6\",\"Name\":\"Dragon\",\"Damage\":10.0}, {\"Id\":\"7\",\"Name\":\"Dragon\",\"Damage\":10.0},{\"Id\":\"8\",\"Name\":\"Dragon\",\"Damage\":10.0},{\"Id\":\"9\",\"Name\":\"Dragon\",\"Damage\":10.0},{\"Id\":\"10\",\"Name\":\"Dragon\",\"Damage\":10.0}]");
        response = new PackageController().createCardPackage(request);
        assertTrue(response.get().contains("201 CREATED"));
    }
}