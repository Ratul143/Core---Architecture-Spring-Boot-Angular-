package jwt.backend.repository;

import jwt.backend.entity.user_management.RecentVisitors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecentVisitorsRepository extends JpaRepository<RecentVisitors, Long> {
    @Query("select rv from RecentVisitors rv where ((lower(rv.visitorName) like %:search%) and (lower(rv.username) like %:search%) and (lower(rv.visitType) like %:search%) and (lower(rv.visitorRole) like %:search%) and (lower(rv.visitorEmail) like %:search%)) order by rv.visitedAt desc")
    List<RecentVisitors> recentVisitorsList(@Param("search") String search, PageRequest pageable);

    Long countAllByVisitedAtNotNull();

}