
document.addEventListener("DOMContentLoaded", () => {

    document.querySelectorAll(".pedido-item").forEach(item => {

        const abrirBotao = item.querySelector(".avaliar-cupcake");
        const formulario = item.querySelector(".avaliacao-form");

        if (!abrirBotao || !formulario) return;

        const cancelarBotao = formulario.querySelector(".cancelar-avaliacao");
        const notaInput = formulario.querySelector(".nota-selecionada");
        const estrelas = formulario.querySelectorAll("[data-nota]");

        function fecharFormulario() {
            formulario.hidden = true;
            abrirBotao.setAttribute("aria-expanded", "false");
        }

        abrirBotao.addEventListener("click", () => {
            const aberto = !formulario.hidden;

            formulario.hidden = aberto;
            abrirBotao.setAttribute("aria-expanded", String(!aberto));
        });

        estrelas.forEach(estrela => {

            estrela.addEventListener("click", () => {

                const nota = Number(estrela.dataset.nota);

                notaInput.value = nota;

                estrelas.forEach(botao => {
                    const valor = Number(botao.dataset.nota);

                    botao.classList.toggle("ativa", valor <= nota);
                    botao.setAttribute(
                        "aria-pressed",
                        String(valor === nota)
                    );
                });

            });

        });

        cancelarBotao.addEventListener("click", () => {
            fecharFormulario();

            notaInput.value = "";

            estrelas.forEach(estrela => {
                estrela.classList.remove("ativa");
                estrela.setAttribute("aria-pressed", "false");
            });
        });

        formulario.addEventListener("submit", event => {

            if (!notaInput.value) {
                event.preventDefault();
                alert("Selecione uma nota antes de enviar sua avaliação.");
            }

        });

    });

});
