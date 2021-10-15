package acme.features.administrator.configuration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import acme.entities.configuration.Configuration;

@Repository
public interface AdministratorConfigurationRepository extends JpaRepository<Configuration,Integer> {

}
