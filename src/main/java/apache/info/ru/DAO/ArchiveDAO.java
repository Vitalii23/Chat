package apache.info.ru.DAO;

import apache.info.ru.Model.Archive;

import java.util.List;

public interface ArchiveDAO {

    Archive get(int archiveId);

    void saveMessageOrUpdate(Archive archive);

    // Delete
    void delete(int archiveId);

    List<Archive> list();
}
