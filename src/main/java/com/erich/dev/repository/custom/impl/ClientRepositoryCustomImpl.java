package com.erich.dev.repository.custom.impl;

import com.erich.dev.dto.proyection.impl.UsuariosDetailsImpl;
import com.erich.dev.entity.*;

import com.erich.dev.repository.custom.ClientRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class ClientRepositoryCustomImpl implements ClientRepositoryCustom {

    @PersistenceContext
    private  EntityManager em;

    @Override
    public List<UsuariosDetailsImpl> countUsersByDateAndRole(LocalDateTime start, LocalDateTime end) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<UsuariosDetailsImpl> query = cb.createQuery(UsuariosDetailsImpl.class);
        Root<Usuario> root = query.from(Usuario.class);

        query.multiselect(root.get(Usuario_.usuariosDate).alias("usuariosDate"), cb.count(root).alias("total"));
        Predicate predicate = cb.between(root.get(AbstractEntity_.creationDate), start, end);

        //obtengo el role y hacemos un join con el usuario
        //como la relacion es manyToMany uso joinSet
        Join<Usuario, Role> roleJoin = root.join(Usuario_.roles);
        //filtramos solo  los ROLE_USER
        Predicate rolePredicate = cb.equal(roleJoin.get(Role_.authority), "ROLE_USER");

        query.where(cb.and(predicate,rolePredicate));

        query.groupBy(root.get(Usuario_.usuariosDate));

       return em.createQuery(query).getResultList();
    }

    @Override
    public Optional<Usuario> findByName(String username) {
        CriteriaBuilder cb  = em.getCriteriaBuilder();
        CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
        Root<Usuario> root = query.from(Usuario.class);
        query.select(root).where(cb.equal(root.get(Usuario_.userName),username));

        Usuario usuario = em.createQuery(query).getResultStream().findFirst().orElse(null);
        return Optional.ofNullable(usuario);
    }
}
