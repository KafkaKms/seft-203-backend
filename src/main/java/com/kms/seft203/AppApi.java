package com.kms.seft203;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
@RequiredArgsConstructor
public class AppApi {

    private final AppVersionRepository appVersionRepo;

    @GetMapping("/version")
    public AppVersion getCurrentVersion() {
        return appVersionRepo
                .findById(1L)
                .orElse(new AppVersion());
    }
}
