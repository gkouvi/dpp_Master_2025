package gr.uoi.dit.master2025.gkouvas.dpp.repository;

import gr.uoi.dit.master2025.gkouvas.dpp.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Document entities.
 *
 * Provides CRUD access and allows retrieving documents
 * that belong to a specific device.
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    /**
     * Finds all documents associated with a specific device.
     *
     * @param deviceId the ID of the device
     * @return list of documents for that device
     */
    List<Document> findByDevice_DeviceId(Long deviceId);
}

