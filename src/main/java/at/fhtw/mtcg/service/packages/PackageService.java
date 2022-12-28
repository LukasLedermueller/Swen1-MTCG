package at.fhtw.mtcg.service.packages;

import at.fhtw.httpserver.http.ContentType;
import at.fhtw.httpserver.http.HttpStatus;
import at.fhtw.httpserver.http.Method;
import at.fhtw.httpserver.server.Request;
import at.fhtw.httpserver.server.Response;
import at.fhtw.httpserver.server.Service;
import at.fhtw.mtcg.controller.packages.PackageController;
import at.fhtw.mtcg.controller.users.SessionController;

public class PackageService implements Service {

    private final PackageController packageController;
    public PackageService() {
        this.packageController = new PackageController();
    }
    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.POST && request.getPathParts().size() == 1) {
            return this.packageController.createCardPackage(request);
        } else {
            return new Response(
                    HttpStatus.BAD_REQUEST,
                    ContentType.PLAIN_TEXT,
                    ""
            );
        }
    }
}
