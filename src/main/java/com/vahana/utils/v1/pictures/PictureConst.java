package com.vahana.utils.v1.pictures;

import java.util.List;

public final class PictureConst {
    public static final List<String> ALLOWED_MIME_TYPES = List.of(
            "image/jpeg",
            "image/png",
            "image/gif",
            "image/webp",
            "image/bmp",
            "image/svg+xml"
    );
}
