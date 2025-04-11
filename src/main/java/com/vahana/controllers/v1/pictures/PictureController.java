package com.vahana.controllers.v1.pictures;

import com.vahana.controllers.v1.BaseController;
import com.vahana.dtos.v1.general.ErrorResponseDTO;
import com.vahana.services.v1.pictures.PictureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@CrossOrigin
@RestController
@Tag(name = "Picture Endpoint", description = "Endpoints for pictures.")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
@RequestMapping("/vahana/api/v1/pictures")
public final class PictureController extends BaseController {
    private final PictureService pictureService;

    @GetMapping("/{id}")
    @Operation(
            operationId = "getPictureById",
            summary = "Get picture by ID [USER, ADMIN]",
            description = "Retrieve a picture by its unique identifier.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "image/png",
                                    schema = @Schema(implementation = byte[].class))
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "image/jpeg",
                                    schema = @Schema(implementation = byte[].class))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    public ResponseEntity<byte[]> getPicture(@PathVariable UUID id) {
        super.InitializeMDC();
        return pictureService.getPicture(id);
    }

    @PutMapping(value = "/{id}")
    @Operation(
            operationId = "updatePictureById",
            summary = "Update picture by ID [USER, ADMIN]",
            description = "Update a picture by its unique identifier.",
            responses = {
                    @ApiResponse(responseCode = "200"),
                    @ApiResponse(
                            responseCode = "401",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "415",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorResponseDTO.class))
                    )
            }
    )
    public ResponseEntity<Void> updatePicture(
            @PathVariable UUID id,
            @RequestParam("picture") MultipartFile file) {
        super.InitializeMDC();
        return pictureService.updatePicture(id, file);
    }
}
