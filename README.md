# GitHub Repositories Explorer

## Descrição

O GitHub Repositories Explorer é um aplicativo Android desenvolvido em Kotlin que permite aos usuários pesquisar e visualizar os repositórios mais populares de Java no GitHub. O aplicativo oferece uma interface amigável e acessível, seguindo as melhores práticas de desenvolvimento, incluindo testes unitários e instrumentados, programação reativa, injeção de dependências e acessibilidade.

## Funcionalidades

- **Lista de Repositórios**:
  - Exibe uma lista dos repositórios Java mais populares no GitHub.
  - Cada item da lista mostra o nome do repositório, descrição, nome e foto do autor, número de estrelas e forks.
  - Implementa paginação com endless scroll para carregar mais repositórios à medida que o usuário rola a lista.

- **Detalhes do Pull Request**:
  - Ao tocar em um item da lista de repositórios, o aplicativo navega para a lista de pull requests do repositório selecionado.
  - Cada pull request exibe o nome do autor, título, data e descrição.

## Tecnologias e Ferramentas Utilizadas

- **Linguagem**: Kotlin
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **Dependências**:
  - Retrofit para chamadas de API
  - Glide para carregamento de imagens
  - Koin para injeção de dependências
  - Espresso e JUnit para testes instrumentados e unitários
- **Programação Reativa**: Utilização de StateFlow para gerenciamento de estados

## Testes

- **Testes Unitários**:
  - Cobertura de 88% com JaCoCo.
  - Testes para ViewModel e lógica de negócios.
  
- **Testes Instrumentados**:
  - Utilização do Espresso e ActivityScenario.
  - Implementação de IdlingResource para gerenciar a sincronização com a UI.
  - Testes de acessibilidade e navegação.

## Acessibilidade

- **Leitores de Tela**:
  - Adicionadas descrições de conteúdo para leitores de tela.
  - Anúncios de carregamento e finalização de carregamento.
  
- **Configurações de Foco**:
  - Garantido que os elementos interativos estejam acessíveis e fáceis de navegar.

## Estrutura do Projeto

- **app/src/main/java**:
  - **model**: Classes de modelo para os dados do GitHub.
  - **network**: Configuração de Retrofit e interfaces de API.
  - **repository**: Repositório para gerenciamento de dados.
  - **ui**: Atividades, fragmentos e adaptadores.
  - **viewmodel**: ViewModels para gerenciamento de lógica de UI.
  - **utils**: Classes utilitárias, como IdlingResource e helpers.

- **app/src/test/java**:
  - Testes unitários para ViewModels e lógica de negócios.

- **app/src/androidTest/java**:
  - Testes instrumentados para UI e navegação.
