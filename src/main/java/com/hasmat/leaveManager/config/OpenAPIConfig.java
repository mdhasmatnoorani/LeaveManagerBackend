package com.hasmat.leaveManager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author Hasmat.Noorani
 * @since 28-12-23
 */
@OpenAPIDefinition(
        info= @Info(
                contact = @Contact(
                        name = "Hasmat Noorani",
                        email = "hasmatnoorani@gmail.com",
                        url = "http://rhombus-module-leave-manager.com"
                ),
                description = "OpenAPI Documentation For Rhombus Module - LeaveManager",
                title = "Rhombus Module - LeaveManager",
                version = "v1",
                license = @License(
                        name = "License Name",
                        url = "http://rhombus-module-leave-manager.com"
                ),
                termsOfService = "Terms of Service"
        ),
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url = "http://localhost:8081"
                )
        }
)
public class OpenAPIConfig {

}
