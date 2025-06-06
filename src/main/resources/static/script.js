
document.addEventListener('DOMContentLoaded', () => {

    // --- Configuração da API ---
    const API_BASE_URL = 'http://localhost:8080/api';

    // --- Seletores de Elementos do DOM (alinhados com o HTML aprimorado) ---
    const allActionButtons = document.querySelectorAll('.accordion button, .input-group button');
    const resultsArea = document.getElementById('results-area');
    const messageArea = document.getElementById('message-area');
    const btnClearResults = document.getElementById('btnClearResults');

    // Inputs
    const searchTitleInput = document.getElementById('searchTitle');
    const searchLanguageInput = document.getElementById('searchLanguage');
    const searchAuthorNameInput = document.getElementById('searchAuthorName');
    const authorBirthYearInput = document.getElementById('authorBirthYear'); // ID corrigido do HTML original
    const authorAliveInYearInput = document.getElementById('authorAliveInYear');

    // Botões
    const btnSearchBook = document.getElementById('btnSearchBook');
    const btnListAllBooks = document.getElementById('btnListAllBooks');
    const btnListAllAuthors = document.getElementById('btnListAllAuthors');
    const btnListBooksByLanguage = document.getElementById('btnListBooksByLanguage');
    const btnListTop10Books = document.getElementById('btnListTop10Books');
    const btnSearchAuthorName = document.getElementById('btnSearchAuthorName');
    const btnListAuthorsByBirthYear = document.getElementById('btnListAuthorsByBirthYear'); // ID corrigido do HTML original
    const btnListAuthorsAliveInYear = document.getElementById('btnListAuthorsAliveInYear');
    const btnGetDownloadStatistics = document.getElementById('btnGetDownloadStatistics');

    // --- Funções de Experiência do Usuário (UX) ---

    // Define o estado inicial da área de resultados
    const setInitialState = () => {
        resultsArea.innerHTML = `
            <div class="results-placeholder">
                <i class="bi bi-inbox"></i>
                <p class="h6">Seus resultados aparecerão aqui.</p>
                <p class="small text-muted">Use as ações ao lado para começar.</p>
            </div>
        `;
        messageArea.classList.add('d-none');
        messageArea.innerHTML = '';
        btnClearResults.classList.add('d-none');
    };
    setInitialState();

    // Mostra/esconde o spinner de carregamento e desabilita/habilita botões
    const toggleLoading = (isLoading) => {
        if (isLoading) {
            resultsArea.innerHTML = `
                <div class="loading-spinner">
                    <div class="spinner-border text-primary" role="status">
                        <span class="visually-hidden">Loading...</span>
                    </div>
                </div>
            `;
            allActionButtons.forEach(button => button.disabled = true);
        } else {
            allActionButtons.forEach(button => button.disabled = false);
        }
    };

    // Exibe uma mensagem de status (sucesso, erro, info)
    const displayMessage = (message, type = 'danger') => {
        messageArea.innerHTML = `<div class="alert alert-${type}" role="alert">${message}</div>`;
        messageArea.classList.remove('d-none');
    };

    // --- Funções de Renderização (Cards em vez de Tabela) ---

    const displayBooks = (data) => {
        // Se a API retorna um único objeto (como na busca por título), coloca-o em um array para poder usar .map
        const bookList = Array.isArray(data) ? data : [data];

        if (bookList.length === 0 || !bookList[0]) {
             resultsArea.innerHTML = `<p class="text-muted text-center p-4">Nenhum livro encontrado.</p>`;
             return;
        }

        const resultsHTML = bookList.map(book => {
            // Adaptação para os nomes dos campos que vêm da sua API
            const title = book.titulo || 'Título não disponível';
            const authors = (book.autores || []).map(a => a.nome).join(', ') || 'Autor desconhecido';
            const languages = (book.idiomas || []).join(', ').toUpperCase() || 'N/A';
            const downloads = (book.numeroDeDownloads || 0).toLocaleString('pt-BR');

            return `
                <div class="book-card">
                    <h5 class="book-card-title mb-1">${title}</h5>
                    <p class="book-card-author"><i class="bi bi-person-fill me-1"></i>${authors}</p>
                    <div class="book-card-details">
                        <span><i class="bi bi-translate me-1"></i>Idioma: <span class="badge bg-secondary">${languages}</span></span>
                        <span><i class="bi bi-download me-1"></i>Downloads: <span class="badge bg-success">${downloads}</span></span>
                    </div>
                </div>
            `;
        }).join('');
        resultsArea.innerHTML = resultsHTML;
    };

    const displayAuthors = (data) => {
        const authorList = Array.isArray(data) ? data : [data];

        if (authorList.length === 0 || !authorList[0]) {
             resultsArea.innerHTML = `<p class="text-muted text-center p-4">Nenhum autor encontrado.</p>`;
             return;
        }

        const resultsHTML = authorList.map(author => {
            const name = author.nome || 'Nome desconhecido';
            const birthYear = author.anoDeNascimento || 'N/A';
            const deathYear = author.anoDeFalecimento || 'N/A';

            return `
                <div class="book-card">
                    <h5 class="book-card-title mb-1"><i class="bi bi-person-fill me-2"></i>${name}</h5>
                    <p class="book-card-author">Nascimento: ${birthYear} - Falecimento: ${deathYear}</p>
                </div>
            `;
        }).join('');
        resultsArea.innerHTML = resultsHTML;
    };

    const displayStatistics = (data) => {
         // Esta função foi baseada na estrutura do seu JSON de estatísticas
         const statsHtml = `
            <div class="book-card p-3">
                 <h5 class="book-card-title text-primary"><i class="bi bi-bar-chart-line-fill me-2"></i>Estatísticas de Downloads</h5>
                 <ul class="list-group list-group-flush">
                    <li class="list-group-item d-flex justify-content-between align-items-center">Média de Downloads: <span class="badge bg-info rounded-pill">${(data.mediaDownloads || 0).toFixed(2)}</span></li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">Maior Número de Downloads: <span class="badge bg-success rounded-pill">${(data.maiorDownload || 0).toLocaleString('pt-BR')}</span></li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">Menor Número de Downloads: <span class="badge bg-warning text-dark rounded-pill">${(data.menorDownload || 0).toLocaleString('pt-BR')}</span></li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">Soma Total de Downloads: <span class="badge bg-primary rounded-pill">${(data.somaDownloads || 0).toLocaleString('pt-BR')}</span></li>
                    <li class="list-group-item d-flex justify-content-between align-items-center">Total de Livros Analisados: <span class="badge bg-secondary rounded-pill">${data.totalLivrosComDownloads || 0}</span></li>
                 </ul>
            </div>
         `;
         resultsArea.innerHTML = statsHtml;
    };

    // --- Lógica Principal de Requisição ---

    // Função genérica para encapsular requisições, tratando loading e erros
    async function handleRequest(apiCallFunction) {
        toggleLoading(true);
        messageArea.classList.add('d-none');
        try {
            await apiCallFunction();
            btnClearResults.classList.remove('d-none');
        } catch (error) {
            console.error('Erro na requisição:', error);
            displayMessage(error.message, 'danger');
            resultsArea.innerHTML = '';
        } finally {
            toggleLoading(false);
        }
    }

    // Função genérica para fazer requisições à API
    async function fetchData(url, successCallback) {
        const response = await fetch(url);
        if (!response.ok) {
            let errorText = 'Erro desconhecido.';
            try {
                // Tenta ler o corpo do erro como texto, pois pode não ser JSON
                errorText = await response.text();
            } catch (e) {
                // Se falhar, usa o status text padrão
                errorText = response.statusText;
            }
            throw new Error(`Erro ${response.status}: ${errorText}`);
        }
        const data = await response.json();
        successCallback(data);
    }

    // --- Funções de API ---

    const searchBookByTitle = () => fetchData(`${API_BASE_URL}/livros/buscar?titulo=${encodeURIComponent(searchTitleInput.value)}`, displayBooks);
    const listAllBooks = () => fetchData(`${API_BASE_URL}/livros`, displayBooks);
    const listAllAuthors = () => fetchData(`${API_BASE_URL}/autores`, displayAuthors);
    const listBooksByLanguage = () => fetchData(`${API_BASE_URL}/livros/idioma/${encodeURIComponent(searchLanguageInput.value)}`, displayBooks);
    const listTop10Books = () => fetchData(`${API_BASE_URL}/livros/top10`, displayBooks);
    const searchAuthorByName = () => fetchData(`${API_BASE_URL}/autores/buscar?nome=${encodeURIComponent(searchAuthorNameInput.value)}`, displayAuthors);
    const listAuthorsByBirthYear = () => fetchData(`${API_BASE_URL}/autores/nascidos-antes/${authorBirthYearInput.value}`, displayAuthors);
    const listAuthorsAliveInYear = () => fetchData(`${API_BASE_URL}/autores/vivos-em/${authorAliveInYearInput.value}`, displayAuthors);
    const getDownloadStatistics = () => fetchData(`${API_BASE_URL}/estatisticas`, displayStatistics);

    // --- Event Listeners ---
    btnClearResults.addEventListener('click', setInitialState);

    btnSearchBook.addEventListener('click', () => handleRequest(searchBookByTitle));
    btnListAllBooks.addEventListener('click', () => handleRequest(listAllBooks));
    btnListAllAuthors.addEventListener('click', () => handleRequest(listAllAuthors));
    btnListBooksByLanguage.addEventListener('click', () => handleRequest(listBooksByLanguage));
    btnListTop10Books.addEventListener('click', () => handleRequest(listTop10Books));
    btnSearchAuthorName.addEventListener('click', () => handleRequest(searchAuthorByName));
    btnListAuthorsByBirthYear.addEventListener('click', () => handleRequest(listAuthorsByBirthYear));
    btnListAuthorsAliveInYear.addEventListener('click', () => handleRequest(listAuthorsAliveInYear));
    btnGetDownloadStatistics.addEventListener('click', () => handleRequest(getDownloadStatistics));
});