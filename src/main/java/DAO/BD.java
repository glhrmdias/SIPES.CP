package DAO;

import controller.PrincipalController;
import model.Localidade;
import model.Orgao;
import model.Setor;
import model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BD {

    public List<Setor> getSetor() {

        Conexao con = new Conexao();

        List<Setor> setores = new ArrayList<Setor>();

        String sql = "SELECT * from setor";

        ResultSet resultSet = con.ExecutaSelect(sql);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    int idSetor = resultSet.getInt("id");
                    String nome = resultSet.getString("nome");
                    String sigla = resultSet.getString("sigla");
                    Setor setor = new Setor();
                    setor.setId(idSetor);
                    setor.setNome(nome);
                    setor.setSigla(sigla);
                    setores.add(setor);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con.fecharConexao();
        return setores;

    }

    public Usuario getUsuarioMatricula(String matricula) {

        Conexao con = new Conexao();

        Usuario usuario = null;

        String sql = "SELECT * from usuario WHERE matricula ='" + matricula + "'";

        System.out.println(sql);

        ResultSet resultSet = con.ExecutaSelect(sql);

        List<Setor> setores = getSetor();
        Map<Integer, Setor> mapSetor = new HashMap<>();

        for(Setor setor : setores) {
            mapSetor.put(setor.getId(), setor);
        }

        if (resultSet != null ) {
            try {
                if (resultSet.next()) {
                    String matric = resultSet.getString("matricula");
                    String nome = resultSet.getString("nome");
                    String senha = resultSet.getString("senha");
                    int setor = resultSet.getInt("setor_id");
                    usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setMatricula(matricula);
                    usuario.setSetor(mapSetor.get(setor));
                    usuario.setSenha(senha);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con.fecharConexao();
        return usuario;

    }

    public List<Orgao> getOrgao() {

        Conexao con = new Conexao();

        List<Orgao> orgaos = new ArrayList<Orgao>();

        String sql = "SELECT * from orgao";

        ResultSet resultSet = con.ExecutaSelect(sql);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    int idOrgao = resultSet.getInt("id");
                    String org = resultSet.getString("orgao");
                    Orgao orgao = new Orgao();
                    orgao.setId(idOrgao);
                    orgao.setOrgao(org);
                    orgaos.add(orgao);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con.fecharConexao();
        return orgaos;

    }

    public List<Localidade> getLocal() {

        Conexao con = new Conexao();

        List<Localidade> locals = new ArrayList<Localidade>();

        String sql = "SELECT * from localidade";

        ResultSet resultSet = con.ExecutaSelect(sql);

        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    int idOrgao = resultSet.getInt("id");
                    String local = resultSet.getString("localidade");
                    Localidade loc = new Localidade();
                    loc.setId(idOrgao);
                    loc.setLocalidade(local);
                    locals.add(loc);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        con.fecharConexao();
        return locals;

    }
}
