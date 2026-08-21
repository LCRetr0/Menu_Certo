// URL base da sua API Spring Boot
const API_URL = window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1'
    ? 'http://localhost:8080/api/produtos'
    : 'https://menu-certo.onrender.com/api/produtos';
// Elementos da Interface DOM
const areaRespostas = document.getElementById('area-respostas');
const formCodigo = document.getElementById('form-codigo');
const formCategoria = document.getElementById('form-categoria');
const selectFiltro = document.getElementById('select-filtro');

/**
 * Alterna a exibição dos formulários de entrada no HTML
 */
function exibirOpcao(opcao) {
    limparRespostas();
    if (opcao === 1) {
        formCodigo.classList.remove('hidden');
        formCategoria.classList.add('hidden');
    } else if (opcao === 2) {
        formCategoria.classList.remove('hidden');
        formCodigo.classList.add('hidden');
        atualizarCamposFiltro(); // Renderiza o sub-campo inicial do filtro
    }
}

/**
 * 1. BUSCA POR CÓDIGO DE BARRAS
 */
document.getElementById('btn-buscar-codigo').addEventListener('click', () => {
    const codigo = document.getElementById('input-codigo').value.trim();

    if (!codigo) {
        exibirMensagem('Por favor, digite um código de barras válido.');
        return;
    }

    exibirMensagem('Buscando produto...');

    fetch(`${API_URL}/codigo/${codigo}`)
        .then(response => {
            if (!response.ok || response.status === 204) {
                throw new Error('Produto não encontrado');
            }
            return response.json();
        })
        .then(produto => {
            if (!produto) {
                exibirMensagem('Produto não encontrado para o código fornecido.');
                return;
            }
            renderizarCardProduto(produto);
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
            exibirMensagem('Produto não encontrado ou erro na busca.');
        });
});

/**
 * 2. BUSCA POR FILTROS / CATEGORIAS
 */
document.getElementById('btn-buscar-categoria').addEventListener('click', () => {
    const tipoFiltro = selectFiltro.value;
    let endpoint = `${API_URL}/buscar?`;

    // Monta a Query String dependendo do filtro selecionado
    if (tipoFiltro === "1") {
        const score = document.getElementById('input-subfiltro').value;
        endpoint += `nutriscore=${score}`;
    } else if (tipoFiltro === "2") {
        const nova = document.getElementById('input-subfiltro').value;
        endpoint += `nova=${nova}`;
    } else if (tipoFiltro === "3") {
        const termo = document.getElementById('input-subfiltro').value;
        endpoint += `termo=${encodeURIComponent(termo)}`;
    } else if (tipoFiltro === "4") {
        const restricao = document.getElementById('input-subfiltro').value;
        endpoint += `restricao=${restricao}`;
    } else if (tipoFiltro === "5") {
        const pais = document.getElementById('input-pais').value || 'brazil';
        const termoPais = document.getElementById('input-subfiltro').value;
        endpoint += `pais=${encodeURIComponent(pais)}&termo=${encodeURIComponent(termoPais)}`;
    }

    exibirMensagem('Buscando produtos...');

    fetch(endpoint)
        .then(response => response.json())
        .then(data => {
            limparRespostas();
            if (!data.products || data.products.length === 0) {
                exibirMensagem('Nenhum produto encontrado para esse filtro.');
                return;
            }
            // Mapeia e renderiza a lista de produtos retornada
            data.products.forEach(prod => renderizarCardProdutoLista(prod));
        })
        .catch(error => {
            console.error('Erro na requisição:', error);
            exibirMensagem('Erro ao buscar produtos.');
        });
});


// Renderiza o ProductDTO (Busca individual por código)
function renderizarCardProduto(p) {
    limparRespostas();

    // Busca o nome do produto testando vários campos comuns da API
    const nomeProduto = p.productName
        || p.product_name_pt
        || p.product_name
        || p.product_name_en
        || 'Nome não cadastrado';

    // Busca a marca em campos alternativos
    const marcas = p.brands || p.brands_tags?.join(', ') || 'Não informada';

    // Busca a quantidade
    const quantidade = p.quantity || p.product_quantity || 'Não informada';

    // Busca os ingredientes testando campos em português e genéricos
    const ingredientes = p.ingredientsText
        || p.ingredients_text_pt
        || p.ingredients_text
        || 'Ingredientes não informados no cadastro do produto';

    // Tratamento de Calorias / Nutrientes
    let kcal = 'N/A';
    if (p.nutriments) {
        kcal = p.nutriments.energyKcal100g
            || p.nutriments['energy-kcal_100g']
            || p.nutriments['energy-kcal']
            || 'N/A';
        if (kcal !== 'N/A') kcal += ' kcal';
    }

    // Tratamento de Imagem
    const imagemUrl = p.imageUrl || p.image_front_url || p.image_url;

    // Nutri-Score
    const nutriscore = (p.nutriscoreGrade || p.nutriscore_grade || 'N/A').toUpperCase();

    const card = document.createElement('div');
    card.className = 'card-produto';
    card.innerHTML = `
        ${imagemUrl ? `<img src="${imagemUrl}" alt="${nomeProduto}" style="max-width: 100%; height: 160px; object-fit: contain; margin-bottom: 10px;">` : ''}
        <h3>${nomeProduto}</h3>
        <p><strong>Marca:</strong> ${marcas}</p>
        <p><strong>Nutri-Score:</strong> ${nutriscore}</p>
        <p><strong>Quantidade:</strong> ${quantidade}</p>
        <p><strong>Calorias (100g):</strong> ${kcal}</p>
        <p><strong>Ingredientes:</strong> ${ingredientes}</p>
    `;
    areaRespostas.appendChild(card);
}

// Renderiza o ProdutoDto (Busca em lista/categoria)
function renderizarCardProdutoLista(p) {
    const nomeProduto = p.product_name_pt
        || p.product_name
        || p.productName
        || 'Nome não cadastrado';

    const marcas = p.brands || 'Não informada';
    const nutriscore = (p.nutriscore_grade || p.nutriscoreGrade || 'N/A').toUpperCase();

    const card = document.createElement('div');
    card.className = 'card-produto';
    card.innerHTML = `
        ${p.image_front_small_url ? `<img src="${p.image_front_small_url}" alt="${nomeProduto}" style="max-width: 100%; height: 120px; object-fit: contain; margin-bottom: 10px;">` : ''}
        <h3>${nomeProduto}</h3>
        <p><strong>Marca:</strong> ${marcas}</p>
        <p><strong>Nutri-Score:</strong> ${nutriscore}</p>
        <p><strong>Código:</strong> ${p.code || 'N/A'}</p>
    `;
    areaRespostas.appendChild(card);
}
function limparRespostas() {
    areaRespostas.innerHTML = '';
}

function exibirMensagem(texto) {
    areaRespostas.innerHTML = `<p style="grid-column: 1/-1; text-align: center; color: #666; font-weight: bold;">${texto}</p>`;
}

// Atualiza dinamica e visualmente os sub-inputs do formulário de filtros conforme o select muda
selectFiltro.addEventListener('change', atualizarCamposFiltro);

function atualizarCamposFiltro() {
    const valor = selectFiltro.value;
    let containerSub = document.getElementById('container-dinamico-filtro');

    if (!containerSub) {
        containerSub = document.createElement('div');
        containerSub.id = 'container-dinamico-filtro';
        containerSub.style.display = 'flex';
        containerSub.style.flexDirection = 'column';
        containerSub.style.gap = '10px';
        formCategoria.insertBefore(containerSub, document.getElementById('btn-buscar-categoria'));
    }

    if (valor === "1") {
        containerSub.innerHTML = `
            <label>Escolha o Nutri-Score:</label>
            <select id="input-subfiltro">
                <option value="a">A</option>
                <option value="b">B</option>
                <option value="c">C</option>
                <option value="d">D</option>
                <option value="e">E</option>
            </select>
        `;
    } else if (valor === "2") {
        containerSub.innerHTML = `
            <label>Grupo NOVA (Processamento):</label>
            <select id="input-subfiltro">
                <option value="1">1 - In natura / Mínimamente processado</option>
                <option value="2">2 - Ingredientes culinários</option>
                <option value="3">3 - Processados</option>
                <option value="4">4 - Ultraprocessados</option>
            </select>
        `;
    } else if (valor === "3") {
        containerSub.innerHTML = `
            <label>Digite o termo/categoria:</label>
            <input type="text" id="input-subfiltro" placeholder="Ex: chocolate, leite, biscoito">
        `;
    } else if (valor === "4") {
        containerSub.innerHTML = `
            <label>Restrição Alimentar:</label>
            <select id="input-subfiltro">
                <option value="gluten">Sem Glúten</option>
                <option value="lactose">Sem Lactose</option>
            </select>
        `;
    } else if (valor === "5") {
        containerSub.innerHTML = `
            <label>Digite o nome do País (em inglês):</label>
            <input type="text" id="input-pais" placeholder="Ex: france, spain, italy">
            <label>Termo/Categoria:</label>
            <input type="text" id="input-subfiltro" placeholder="Ex: cheese, soda">
        `;
    }
}