package com.kalanblow.school_management.application.usecase.student;

public record StudentPageCacheKey (int page,
                                   int size,
                                   String firstName,
                                   String lastName,
                                   Long classId){
}
