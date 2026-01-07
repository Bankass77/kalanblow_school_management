package com.kalanblow.school_management.application.dto.page;

import java.util.List;

public record PageResponse<T>(List<T> content,
                              int page, int size, long totalElements, int totalPages, boolean last) {
}
