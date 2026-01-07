package com.kalanblow.school_management.application.usecase.classe;

public record SchoolClassePageCacheKey(int page,
                                       int size,
                                       String name,
                                       Long classId) {
}
