package com.kalanblow.school_management.application.usecase;

public record SchoolClassePageCacheKey(int page,
                                       int size,
                                       String name) {
}
