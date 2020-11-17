package com.flywithus.planes.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.flywithus.planes.ddd.Plane;


public interface PlaneRepository extends ReactiveCrudRepository<Plane,String>
{
	
}
