package br.com.fiap;

import br.com.fiap.domain.entity.*;
        import jakarta.persistence.EntityManager;
        import jakarta.persistence.EntityManagerFactory;
        import jakarta.persistence.Persistence;
        import jakarta.persistence.TypedQuery;

        import javax.swing.*;
        import java.time.LocalDate;
import java.util.List;

public class Main {
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("oracle");
    private static EntityManager manager = factory.createEntityManager();


    public static void main(String[] args) {
        
        manager.getTransaction().begin();

        boolean continuar = true;

        while (continuar) {
            int escolha = showMenu();

            switch (escolha) {
                case 0:
                    System.out.println("Inserir bens selecionado: ");
                    Departamento dep =  selectDepartamento();
                    inserirInventario(dep);
                    inserirBens(dep);
                    manager.getTransaction().commit();
                    break;
                case 1:
                    System.out.println("Consultar bens selecionado: ");
                    consultaTodosBens();
                    break;
                case 2:
                    System.out.println("Consultar inventario selecionado: ");
                    consultaInventario();
                    break;
                case 3:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
        manager.close();
        factory.close();
    }

    private static void consultaInventario() {
        String id_inventario = JOptionPane.showInputDialog( "Insira o id do inventario: " );
        manager.find(Inventario.class, id_inventario);
        Inventario i = manager.find(Inventario.class, id_inventario);
        if (i != null) {
            
            System.out.println("Inventario encontrado!");
            System.out.println("Info: " + i);
        } else {
            System.out.println("Inventario não encontrado!");
        }
        System.out.println("Pesquisa de inventário encerrada.");
    }

    private static void consultaTodosBens() {
        try {
            TypedQuery<Bem> query = manager.createQuery("SELECT b FROM Bem b", Bem.class);
            List<Bem> bens = query.getResultList();
            if (bens.isEmpty()) {
                System.out.println("Não há bens cadastrados!");
            } else {
                for (Bem b : bens) {
                    System.out.println("Bem: " + b);
                }
            }
        } finally {
            System.out.println("Pesquisa encerrada.");
        }
    }

    private static TipoDeBem selectTipoDeBem() {
        TypedQuery<TipoDeBem> query = manager.createQuery("SELECT tb FROM TipoDeBem tb", TipoDeBem.class);
        List<TipoDeBem> options = query.getResultList();
        TipoDeBem tipoSelecionado = (TipoDeBem) JOptionPane.showInputDialog(
                null,
                "Selecione um tipo de bem",
                "Seleção de Tipo de bem",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options.toArray(),
                options.get( 0 )
        );
        return tipoSelecionado;
    }

    private static Departamento selectDepartamento() {
        TypedQuery<Departamento> query = manager.createQuery("SELECT d FROM Departamento d", Departamento.class);
        List<Departamento> options = query.getResultList();
        Departamento selectedDpto = (Departamento) JOptionPane.showInputDialog(
                null,
                "Selecione o Departamento",
                "Seleção de Departamento",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options.toArray(),
                options.get( 0 )
        );
        return selectedDpto;
    }

    private static Bem inserirBens(Departamento dep_localizacao) {
        String nomeDoBem = JOptionPane.showInputDialog("Insira o nome do bem: ");
        TipoDeBem tipo = selectTipoDeBem();
        String etiqueta = JOptionPane.showInputDialog("insira a etiqueta: ");
        try {
            Bem bem = new Bem(null, nomeDoBem, tipo, etiqueta, dep_localizacao, LocalDate.now() );
            // Bem{id=null, nome='nomeDoBem666', tipo=TipoDeBem{id=805, nome='maquina'}, etiqueta='etiquetaDoBem', localizacao=Departamento{id=1552, nome='Departamento B'}, aquisicao=2023-08-31}
            manager.persist(bem);
            System.out.println("Bem inserido.");
            return bem;
        } catch (Exception e) {
            System.out.println("Erro! em inserirBens(): " + e);
        }
        return null;
    }

    private static void inserirInventario(Departamento departamentoSelecionado) {
        try {
            Inventario inventario = new Inventario(
                    null,
                    departamentoSelecionado,
                    LocalDate.now(),
                    LocalDate.now().plusDays(7),
                    "Relatório"
            );
            System.out.println("Inventário: "+ inventario);
            manager.persist(inventario);
            System.out.println("Inventário criado com sucesso: " + inventario);
//            return inventario;

        } catch (Exception e) {
            System.out.println("Erro em inserirInventario(): " + e);
        }
//        return null;
    }

    private static Departamento inserirDepartamento(Departamento dep) {
        // primeiro verifica se departamento ja existe
        TypedQuery<Departamento> query = manager.createQuery("SELECT d FROM Departamento d", Departamento.class);
        List<Departamento> departamentos = query.getResultList();
        if (departamentos.isEmpty()) {
            criarDepartamentos();
            inserirDepartamento(dep);
            return null;
        } else {
            for (Departamento d: departamentos) {
                if (d.getId() == dep.getId()) {
                    System.out.println("Departamento já cadastrado!");
                    return dep; // retorna o ja cadastrado
                }
            }
            try {
                manager.persist( dep );
                System.out.println("departamento: " + dep +" cadastrado.");
                return dep;
            } catch (Exception e) {
                System.out.println("Erro! em inserirDepartamento(): " + e);
                return null;
            }
        }
    }

    private static void criarDepartamentos() {
        for (EnumDepartamento dep: EnumDepartamento.values()) {
            try {
                manager.persist(dep.getDepartamento());
                System.out.println(dep.getDepartamento());
            } catch (Exception e) {
                System.out.println("Erro em criarDepartamentos(): " + e);
            }
        }
    }

    private static int showMenu() {
        String[] opcoes = {"Inserir Bem", "Consultar Todos os Bens", "Consultar inventario", "Sair"};

        return JOptionPane.showOptionDialog(
                null,
                "Escolha uma opção:",
                "Menu",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
    }
}