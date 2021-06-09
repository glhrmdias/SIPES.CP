package DAO;

import model.Movimentacao;
import model.Usuario;

public class MovimentacaoDAO {

    public boolean cadastroMovimentacao(Movimentacao movimentacao) {

        Conexao con = new Conexao();

        String sql = "INSERT into movimentacao (dt_registro, setor_id, atividade, assunto," +
                "processo, orgao_id, local_id, dt_inicio, dt_fim, hr_inicio, hr_fim, conclusao, observacao, usuario)"
                + " VALUES('"
                + movimentacao.getDataRegistro()
                + "', '" + movimentacao.getSetor().getId()
                + "', '" + movimentacao.getAtividade()
                + "', '" + movimentacao.getAssunto()
                + "', '" + movimentacao.getProcesso()
                + "', '" + movimentacao.getOrgao().getId()
                + "', '" + movimentacao.getLocal().getId()
                + "', '" + movimentacao.getDataInicio()
                + "', '" + movimentacao.getDataFim()
                + "', '" + movimentacao.getHoraInicio()
                + "', '" + movimentacao.getHoraFim()
                + "', '" + movimentacao.getConclusao()
                + "', '" + movimentacao.getObervação()
                + "', '" + movimentacao.getUsuario()
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
