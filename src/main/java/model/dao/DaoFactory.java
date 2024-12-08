package model.dao;

import db.DB;
import model.dao.impl.*;

public class DaoFactory {
    public static UsuarioDao createUsuarioDao() {
        return new UsuarioDaoJDBC(DB.getConnection());
    }

    public static LivroDao createLivroDao() {
        return new LivroDaoJDBC(DB.getConnection());
    }

    public static CategoriaDao createCategoriaDao() {
        return new CategoriaDaoJDBC(DB.getConnection());
    }

    public static AutorDao createAutorDao() {
        return new AutorDaoJDBC(DB.getConnection());
    }

    public static EditoraDao createEditoraDao() {
        return new EditoraDaoJDBC(DB.getConnection());
    }
    
    public static EmprestimoDao createEmprestimoDao() {
        return new EmprestimoDaoJDBC(DB.getConnection());
    }
}
