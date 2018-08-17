package drools.spring.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import drools.spring.example.facts.Illness;

public interface IllnessRepository extends JpaRepository<Illness, Long> {
	Illness findByName(String name);
}
