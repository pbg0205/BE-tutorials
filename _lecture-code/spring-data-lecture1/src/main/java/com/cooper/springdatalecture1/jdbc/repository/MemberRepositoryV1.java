package com.cooper.springdatalecture1.jdbc.repository;

import com.cooper.springdatalecture1.jdbc.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;


/**
 * JDBC - DataSource 사용, JdbcUtils 사용
 *
 */
@Slf4j
@RequiredArgsConstructor
public class MemberRepositoryV1 {

    private final DataSource dataSource;

    public Member save(Member member) throws SQLException {
        String sql = "insert into member(member_id, money) values (?, ?)";

        try (
                Connection connection = getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql);
        ) {
            psmt.setString(1, member.getMemberId());
            psmt.setInt(2, member.getMoney());
            psmt.executeUpdate();

            return member;
        } catch (SQLException sqlException) {
            log.error("db error", sqlException);
            throw sqlException;
        } finally {
//            JdbcUtils.closeResultSet(resultSet);
//            JdbcUtils.closeStatement(statement);
//            JdbcUtils.closeConnection(connection);
        }
    }

    public Member findById(String memberId) throws SQLException {
        String sql = "select * from member where member_id=?";

        try (
                Connection connection = getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setString(1, memberId);
            ResultSet resultSet = psmt.executeQuery();

            if (resultSet.next()) {
                Member member = new Member();
                member.setMemberId(resultSet.getString("member_id"));
                member.setMoney(resultSet.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not memberId=" + memberId);
            }

        } catch (SQLException sqlException) {
            log.error("db error", sqlException);
            throw sqlException;
        } finally {
//            JdbcUtils.closeResultSet(resultSet);
//            JdbcUtils.closeStatement(statement);
//            JdbcUtils.closeConnection(connection);
        }

    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        try (
                Connection connection = getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setInt(1, money);
            psmt.setString(2, memberId);
            int resultSize = psmt.executeUpdate();
            log.info("result size = {}", resultSize);
        } catch (SQLException sqlException) {
            log.error("db error ", sqlException);
            throw sqlException;
        } finally {
//            JdbcUtils.closeResultSet(resultSet);
//            JdbcUtils.closeStatement(statement);
//            JdbcUtils.closeConnection(connection);

        }

    }


    public int delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ? ";

        try (
                Connection connection = getConnection();
                PreparedStatement psmt = connection.prepareStatement(sql)
        ) {
            psmt.setString(1, memberId);
            int result = psmt.executeUpdate();
            return result;
        } catch (SQLException sqlException) {
            log.error("db error ", sqlException);
            throw sqlException;
        } finally {
//            JdbcUtils.closeResultSet(resultSet);
//            JdbcUtils.closeStatement(statement);
//            JdbcUtils.closeConnection(connection);
        }

    }

    private Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        log.info("get connection={}, class={}", connection, connection.getClass());
        return connection;
    }

}
