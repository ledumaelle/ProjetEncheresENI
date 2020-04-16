package com.eni.encheres.dal.categories;

import com.eni.encheres.bo.Categorie;

import java.util.List;

/**
 * Interface CategorieDAO
 */
public interface CategorieDAO {

    public List<Categorie> getLesCategories();

    Categorie getCategorieById(int id);

    void update(Categorie categorie);

    int creer(Categorie categorie);

    void delete(int id);
}
