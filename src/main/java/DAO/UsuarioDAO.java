    package DAO;

    import model.Usuario;

    public class UsuarioDAO {

        public boolean cadastroUsuario(Usuario usuario) {

            Conexao con = new Conexao();

            String sql = "INSERT into usuario (nome, matricula, setor_id, senha)"
                    + " VALUES('"
                    + usuario.getNome()
                    + "', '" + usuario.getMatricula()
                    + "', '" + usuario.getSetor().getId()
                    + "', '" + usuario.getSenha()
                    + "');";

            System.out.println(sql);

            int res = con.ExecutaSQL(sql);

            con.fecharConexao();

            if (res != 0) {
                return true;
            } else {
                return false;
            }
        }

    }
