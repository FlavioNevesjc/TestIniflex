import dominio.Funcionario;
import dominio.Pessoa;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // 3.1 - Inserir todos os funcionários
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarios.add(new Funcionario("Maria", LocalDate.of(2000, 10, 18), new BigDecimal("2009.44"), "Operador"));
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 5, 12), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.of(1961, 5, 2), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.of(1988, 10, 14), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.of(1995, 1, 5), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.of(1999, 11, 19), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.of(1993, 3, 31), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.of(1994, 7, 8), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(2003, 5, 24), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Heloísa", LocalDate.of(1996, 9, 2), new BigDecimal("2799.93"), "Gerente"));


        // 3.2 - Remover o funcionário "João"
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 - Imprimir todos os funcionários
        System.out.println("Funcionários:");
        funcionarios.forEach(funcionario -> {
            System.out.printf("Nome: %s | Data de Nascimento: %s | Salário: %s | Função: %s\n",
                    funcionario.getNome(), funcionario.formatarData(), funcionario.formatarSalario(), funcionario.getFuncao());
        });

        // 3.4 - Aplicar aumento de 10% no salário dos funcionários
        funcionarios.forEach(funcionario -> funcionario.getSalario().multiply(BigDecimal.ONE.add(BigDecimal.valueOf(0.10))));

        // 3.5 - Agrupar funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 - Imprimir os funcionários agrupados por função
        System.out.println("\nFuncionários agrupados por função:");
        funcionariosPorFuncao.forEach((funcao, lista) -> {
            System.out.println("Função: " + funcao);
            lista.forEach(funcionario -> {
                System.out.printf("Nome: %s | Data de Nascimento: %s | Salário: %s\n",
                        funcionario.getNome(), funcionario.formatarData(), funcionario.formatarSalario());
            });
        });

        // 3.8 - Imprimir funcionários que fazem aniversário em outubro (mês 10) e dezembro (mês 12)
        System.out.println("\nFuncionários que fazem aniversário em outubro e dezembro:");
        funcionarios.stream()
                .filter(funcionario -> funcionario.getDataNascimento().getMonthValue() == 10 ||
                        funcionario.getDataNascimento().getMonthValue() == 12)
                .forEach(funcionario -> {
                    System.out.printf("Nome: %s | Data de Nascimento: %s\n",
                            funcionario.getNome(), funcionario.formatarData());
                });

        // 3.9 - Encontrar funcionário com maior idade
        Comparator<Pessoa> comparadorPorIdade = Comparator.comparing(pessoa ->
                pessoa.getDataNascimento().until(LocalDate.now()).getYears());

        Pessoa maisVelho = funcionarios.stream()
                .max(comparadorPorIdade)
                .orElse(null);

        if (maisVelho != null) {
            int idade = maisVelho.getDataNascimento().until(LocalDate.now()).getYears();
            System.out.printf("\nFuncionário mais velho: Nome: %s | Idade: %d anos\n",
                    maisVelho.getNome(), idade);
        }

        // 3.10 - Ordenar funcionários por ordem alfabética
        System.out.println("\nFuncionários ordenados por ordem alfabética:");
        funcionarios.stream()
                .sorted(Comparator.comparing(Funcionario::getNome))
                .forEach(funcionario -> {
                    System.out.printf("Nome: %s | Data de Nascimento: %s | Salário: %s\n",
                            funcionario.getNome(), funcionario.formatarData(), funcionario.formatarSalario());
                });

        // 3.11 - Calcular e imprimir o total dos salários dos funcionários
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.printf("\nTotal dos salários dos funcionários: %,.2f\n", totalSalarios);

        // 3.12 - Imprimir quantos salários mínimos cada funcionário ganha (R$1212.00)
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        System.out.println("\nSalários em múltiplos de salário mínimo:");
        funcionarios.forEach(funcionario -> {
            BigDecimal multiplo = funcionario.getSalario().divide(salarioMinimo, 2, BigDecimal.ROUND_DOWN);
            System.out.printf("Funcionário %s ganha %.2f salários mínimos\n", funcionario.getNome(), multiplo);
        });
    }
}