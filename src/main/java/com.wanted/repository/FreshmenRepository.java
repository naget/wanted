package com.wanted.repository;

import com.wanted.model.Freshmen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by t on 2017/2/27.
 */
@Repository
public interface FreshmenRepository extends JpaRepository<Freshmen,Long>{
List<Freshmen> findAll();
Freshmen findByName(String name);
}
