package apache.info.ru.DAO;

import javax.sql.DataSource;

import apache.info.ru.Model.Archive;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArchiveDAOImpl implements ArchiveDAO {

    private JdbcTemplate jdbcTemplate;

    public ArchiveDAOImpl(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public ArchiveDAOImpl() {

    }

    // Insert or update
    @Override
    public void saveMessageOrUpdate(Archive archive){
        if(archive.getId() > 0){
            //update
            String sql = "UPDATE archive SET text=?, date=? WHERE id=?";
            jdbcTemplate.update(sql, archive.getText(), archive.getDate());
        } else {
            //insert
            String sql = "INSERT INTO archive (text, date)" + "VALUES (?, ?)";
            jdbcTemplate.update(sql, archive.getText(), archive.getDate());
        }
    }

    // Delete
    @Override
    public  void delete(int archiveId){
        String sql = "DELETE FROM archive WHERE id =?";
        jdbcTemplate.update(sql, archiveId);
    }

    //List all
    @Override
    public List<Archive> list(){
        String sql = "SELECT * FROM archive";
        List<Archive> listArchive;
        listArchive = jdbcTemplate.query(sql, new RowMapper<Archive>() {

            @Override
            public Archive mapRow(ResultSet resultSet, int i) throws SQLException {
                if(resultSet.next()){
                    Archive arc = new Archive();
                    arc.setId(resultSet.getInt("id"));
                    arc.setText(resultSet.getString("Text"));
                    arc.setDate(resultSet.getDate("Date"));
                    return arc;
                }
                return null;
            }
        });

        return listArchive;
    }

    @Override
    public  Archive get(int archiveId){
        String sql = "SELECT * FROM archive WHERE id =" + archiveId;
        return jdbcTemplate.query(sql, new ResultSetExtractor<Archive>() {
            @Override
            public Archive extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                if(resultSet.next()){
                    Archive archive = new Archive();
                    archive.setId(resultSet.getInt("id"));
                    archive.setText(resultSet.getString("Text"));
                    archive.setDate(resultSet.getDate("Date"));
                }
                return null;
            }
        });
    }

}