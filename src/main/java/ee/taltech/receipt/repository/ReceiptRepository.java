package ee.taltech.receipt.repository;

import ee.taltech.receipt.model.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReceiptRepository extends JpaRepository<Receipt, Long> {

    @Modifying
    @Query("UPDATE Receipt SET fileName = null WHERE fileName = :filename")
    void removeFile(@Param("filename") String filename);

    Optional<Receipt> findAllByFileName(String filename);

}
