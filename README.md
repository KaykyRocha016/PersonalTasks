# PersonalTasks
# PersonalTasks - README

## Descrição

PersonalTasks é um aplicativo Android para gerenciar tarefas pessoais, permitindo criar, editar, visualizar e excluir tarefas com data limite. Utiliza arquitetura MVC e persiste dados localmente usando Room.

---

## Funcionalidades

* Listar tarefas com título, descrição e data limite
* Criar novas tarefas
* Editar tarefas existentes
* Visualizar tarefas em modo somente leitura
* Excluir tarefas com confirmação
* Persistência local via Room
* Interface responsiva com RecyclerView e ViewBinding

---

## Tecnologias usadas

* Kotlin
* Android Jetpack (Room, ViewBinding, Coroutines)
* MVC (Model-View-Controller)
* RecyclerView

---
## Como usar

### 1. Clonar o repositório

```bash
git clone https://github.com/KaykyRocha016/PersonalTasks.git
cd PersonalTasks

```

### 2. Abrir no Android Studio

* Abra o projeto no Android Studio
* Aguarde o download das dependências

### 3. Rodar o aplicativo

* Conecte um dispositivo ou use um emulador Android
* Execute o app (Run > Run 'app')

### 4. Navegação básica

* Tela inicial mostra a lista de tarefas
* Toque no menu (+) para adicionar nova tarefa
* Clique longo numa tarefa para editar, excluir ou visualizar
* Preencha o formulário com título, descrição e data limite
* Botão salvar grava a tarefa no banco local
* Botão cancelar fecha o formulário sem salvar

---



## Observações

* O projeto usa UUID para identificar tarefas unicamente
* O banco local é gerenciado pelo Room com conversores para UUID
* Todas operações de banco são feitas com corrotinas para não bloquear a UI
* Use Android Studio para abrir, compilar e executar o projeto
## Link para o vídeo do youtube
video exemplo de uso https://youtu.be/A_JaT5Wh5E8?si=KHpTca306cY_VGYegit 
## link para o vídeo da demonstração do login, registro e banco de dados com firebase
https://www.youtube.com/watch?v=wg0q30LT-nQ
---


