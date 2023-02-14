package dev.miguel.backendmobile.controller;

import dev.miguel.backendmobile.domain.dto.MobileDTO;
import dev.miguel.backendmobile.service.MobileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class MobileController {

    private final MobileService mobileService;

    @PostMapping("/save")
    public ResponseEntity<MobileDTO> save(@RequestBody MobileDTO mobileDTO){
        mobileService.save(mobileDTO);
        return new ResponseEntity<>(mobileDTO, HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public ResponseEntity<List<MobileDTO>> list(){
        return new ResponseEntity<>(mobileService.findAll(), HttpStatus.OK);
    }
}
