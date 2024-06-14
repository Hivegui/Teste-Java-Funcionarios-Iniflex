import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

class Pessoa {
    private String nome;
    private LocalDate dataNascimento;

    public Pessoa(String nome, LocalDate dataNascimento) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Data de Nascimento: " + dataNascimento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}

class Funcionario extends Pessoa {
    private BigDecimal salario;
    private String funcao;

    public Funcionario(String nome, LocalDate dataNascimento, BigDecimal salario, String funcao) {
        super(nome, dataNascimento);
        this.salario = salario;
        this.funcao = funcao;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public void setSalario(BigDecimal salario) {
        this.salario = salario;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    @Override
    public String toString() {
        return super.toString() + ", Salário: " + salario + ", Função: " + funcao;
    }
}

public class ClassePrincipal {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Método para inserir funcionários com base nos dados da tabela
    private static List<Funcionario> inserirFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        String[] nomes = {"Maria", "João", "Caio", "Miguel", "Alice", "Heitor", "Arthur", "Laura", "Heloísa", "Helena"};
        String[] datasNascimento = {"18/10/2000", "12/05/1990", "02/05/1961", "14/10/1988", "05/01/1995",
                "19/11/1999", "31/03/1993", "08/07/1994", "24/05/2003", "02/09/1996"};
        BigDecimal[] salarios = {new BigDecimal("2009.44"), new BigDecimal("2284.38"), new BigDecimal("9836.14"),
                new BigDecimal("19119.88"), new BigDecimal("2234.88"), new BigDecimal("1582.72"),
                new BigDecimal("4071.84"), new BigDecimal("3017.45"), new BigDecimal("1606.85"),
                new BigDecimal("2799.93")};
        String[] funcoes = {"Operador", "Operador", "Coordenador", "Diretor", "Recepcionista", "Operador",
                "Contador", "Gerente", "Eletricista", "Gerente"};

        for (int i = 0; i < nomes.length; i++) {
            LocalDate dataNascimento = LocalDate.parse(datasNascimento[i], formatter);
            Funcionario funcionario = new Funcionario(nomes[i], dataNascimento, salarios[i], funcoes[i]);
            funcionarios.add(funcionario);
        }

        return funcionarios;
    }

    // Método para remover o funcionário "João" da lista
    private static void removerJoao(List<Funcionario> funcionarios) {
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));
    }

    // método para imprimir todos os funcionários com suas informações
    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            System.out.println(funcionario);
        }
    }

    // método para receber os 10% de aumento de salário
    private static void aumentarSalario(List<Funcionario> funcionarios) {
        for (Funcionario funcionario : funcionarios) {
            BigDecimal aumento = funcionario.getSalario().multiply(new BigDecimal("0.10"));
            BigDecimal novoSalario = funcionario.getSalario().add(aumento);
            funcionario.setSalario(novoSalario);
        }
    }

    // método para mapear os funcionários por função
    private static Map<String, List<Funcionario>> agruparPorFuncao(List<Funcionario> funcionarios) {
        Map<String, List<Funcionario>> funcionariosPorFuncao = new HashMap<>();
        for (Funcionario funcionario : funcionarios) {
            String funcao = funcionario.getFuncao();
            if (!funcionariosPorFuncao.containsKey(funcao)) {
                funcionariosPorFuncao.put(funcao, new ArrayList<>());
            }
            funcionariosPorFuncao.get(funcao).add(funcionario);
        }
        return funcionariosPorFuncao;
    }

    // método para imprimir os funcionários por função
    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            for (Funcionario funcionario : entry.getValue()) {
                System.out.println(funcionario);
            }
            System.out.println();
        }
    }

    // método para imprimir os funcionários que fazem aniversário nos meses 10 e 12
    private static void imprimirAniversariantes(List<Funcionario> funcionarios) {
        System.out.println("Aniversariantes nos meses 10 e 12:");
        for (Funcionario funcionario : funcionarios) {
            int mesAniversario = funcionario.getDataNascimento().getMonthValue();
            if (mesAniversario == 10 || mesAniversario == 12) {
                System.out.println(funcionario);
            }
        }
    }

    // método para imprimir o funcionário com a maior idade
    private static void imprimirFuncionarioMaisVelho(List<Funcionario> funcionarios) {
        Funcionario maisVelho = Collections.min(funcionarios, Comparator.comparing(Funcionario::getDataNascimento));
        LocalDate hoje = LocalDate.now();
        int idade = hoje.getYear() - maisVelho.getDataNascimento().getYear();
        if (hoje.getDayOfYear() < maisVelho.getDataNascimento().getDayOfYear()) {
            idade--;
        }
        System.out.println("Funcionário mais velho:");
        System.out.println("Nome: " + maisVelho.getNome() + ", Idade: " + idade);
    }

    // método para imprimir a lista de funcionários por ordem alfabética
    private static void imprimirFuncionariosOrdenados(List<Funcionario> funcionarios) {
        System.out.println("Lista de funcionários por ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(System.out::println);
    }

    // método para imprimir o total dos salários dos funcionários
    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total dos salários dos funcionários: " + totalSalarios);
    }

    // método para imprimir quantos salários mínimos cada funcionário recebe
    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios) {
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("Salários mínimos ganhos por cada funcionário:");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal salariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_HALF_UP);
            System.out.println(funcionario.getNome() + ": " + salariosMinimos);
        }
    }

    public static void main(String[] args) {
        List<Funcionario> funcionarios = inserirFuncionarios();
        removerJoao(funcionarios);

        System.out.println("Funcionários antes do aumento de salário:");
        imprimirFuncionarios(funcionarios);

        aumentarSalario(funcionarios);

        System.out.println("\nFuncionários após o aumento de salário:");
        imprimirFuncionarios(funcionarios);

        Map<String, List<Funcionario>> funcionariosPorFuncao = agruparPorFuncao(funcionarios);

        System.out.println("\nFuncionários agrupados por função:");
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        imprimirAniversariantes(funcionarios);

        imprimirFuncionarioMaisVelho(funcionarios);

        imprimirFuncionariosOrdenados(funcionarios);

        imprimirTotalSalarios(funcionarios);

        imprimirSalariosMinimos(funcionarios);
    }
}

