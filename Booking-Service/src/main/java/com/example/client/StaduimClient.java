package com.example.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.entity.Stadium;

@FeignClient(name="STADIUM-SERVICE")
public interface StaduimClient {
	@GetMapping("/getStadiumById/{stadiumId}")
	Stadium getStadiumById(@PathVariable int id);
}
