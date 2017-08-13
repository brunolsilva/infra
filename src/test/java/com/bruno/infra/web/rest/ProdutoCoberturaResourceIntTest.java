package com.bruno.infra.web.rest;

import com.bruno.infra.InfraApp;

import com.bruno.infra.domain.ProdutoCobertura;
import com.bruno.infra.repository.ProdutoCoberturaRepository;
import com.bruno.infra.service.ProdutoCoberturaService;
import com.bruno.infra.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProdutoCoberturaResource REST controller.
 *
 * @see ProdutoCoberturaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = InfraApp.class)
public class ProdutoCoberturaResourceIntTest {

    private static final Integer DEFAULT_COBERTURA = 9999;
    private static final Integer UPDATED_COBERTURA = 9998;

    private static final Integer DEFAULT_PRODUTO = 9999;
    private static final Integer UPDATED_PRODUTO = 9998;

    private static final LocalDate DEFAULT_DATA_INICIO_VIGENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_INICIO_VIGENCIA = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_DATA_FIM_VIGENCIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FIM_VIGENCIA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ProdutoCoberturaRepository produtoCoberturaRepository;

    @Autowired
    private ProdutoCoberturaService produtoCoberturaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restProdutoCoberturaMockMvc;

    private ProdutoCobertura produtoCobertura;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProdutoCoberturaResource produtoCoberturaResource = new ProdutoCoberturaResource(produtoCoberturaService);
        this.restProdutoCoberturaMockMvc = MockMvcBuilders.standaloneSetup(produtoCoberturaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProdutoCobertura createEntity() {
        ProdutoCobertura produtoCobertura = new ProdutoCobertura()
            .cobertura(DEFAULT_COBERTURA)
            .produto(DEFAULT_PRODUTO)
            .dataInicioVigencia(DEFAULT_DATA_INICIO_VIGENCIA)
            .dataFimVigencia(DEFAULT_DATA_FIM_VIGENCIA);
        return produtoCobertura;
    }

    @Before
    public void initTest() {
        produtoCoberturaRepository.deleteAll();
        produtoCobertura = createEntity();
    }

    @Test
    public void createProdutoCobertura() throws Exception {
        int databaseSizeBeforeCreate = produtoCoberturaRepository.findAll().size();

        // Create the ProdutoCobertura
        restProdutoCoberturaMockMvc.perform(post("/api/produto-coberturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoCobertura)))
            .andExpect(status().isCreated());

        // Validate the ProdutoCobertura in the database
        List<ProdutoCobertura> produtoCoberturaList = produtoCoberturaRepository.findAll();
        assertThat(produtoCoberturaList).hasSize(databaseSizeBeforeCreate + 1);
        ProdutoCobertura testProdutoCobertura = produtoCoberturaList.get(produtoCoberturaList.size() - 1);
        assertThat(testProdutoCobertura.getCobertura()).isEqualTo(DEFAULT_COBERTURA);
        assertThat(testProdutoCobertura.getProduto()).isEqualTo(DEFAULT_PRODUTO);
        assertThat(testProdutoCobertura.getDataInicioVigencia()).isEqualTo(DEFAULT_DATA_INICIO_VIGENCIA);
        assertThat(testProdutoCobertura.getDataFimVigencia()).isEqualTo(DEFAULT_DATA_FIM_VIGENCIA);
    }

    @Test
    public void createProdutoCoberturaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produtoCoberturaRepository.findAll().size();

        // Create the ProdutoCobertura with an existing ID
        produtoCobertura.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restProdutoCoberturaMockMvc.perform(post("/api/produto-coberturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoCobertura)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ProdutoCobertura> produtoCoberturaList = produtoCoberturaRepository.findAll();
        assertThat(produtoCoberturaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkCoberturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoCoberturaRepository.findAll().size();
        // set the field null
        produtoCobertura.setCobertura(null);

        // Create the ProdutoCobertura, which fails.

        restProdutoCoberturaMockMvc.perform(post("/api/produto-coberturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoCobertura)))
            .andExpect(status().isBadRequest());

        List<ProdutoCobertura> produtoCoberturaList = produtoCoberturaRepository.findAll();
        assertThat(produtoCoberturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkProdutoIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoCoberturaRepository.findAll().size();
        // set the field null
        produtoCobertura.setProduto(null);

        // Create the ProdutoCobertura, which fails.

        restProdutoCoberturaMockMvc.perform(post("/api/produto-coberturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoCobertura)))
            .andExpect(status().isBadRequest());

        List<ProdutoCobertura> produtoCoberturaList = produtoCoberturaRepository.findAll();
        assertThat(produtoCoberturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDataInicioVigenciaIsRequired() throws Exception {
        int databaseSizeBeforeTest = produtoCoberturaRepository.findAll().size();
        // set the field null
        produtoCobertura.setDataInicioVigencia(null);

        // Create the ProdutoCobertura, which fails.

        restProdutoCoberturaMockMvc.perform(post("/api/produto-coberturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoCobertura)))
            .andExpect(status().isBadRequest());

        List<ProdutoCobertura> produtoCoberturaList = produtoCoberturaRepository.findAll();
        assertThat(produtoCoberturaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllProdutoCoberturas() throws Exception {
        // Initialize the database
        produtoCoberturaRepository.save(produtoCobertura);

        // Get all the produtoCoberturaList
        restProdutoCoberturaMockMvc.perform(get("/api/produto-coberturas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produtoCobertura.getId())))
            .andExpect(jsonPath("$.[*].cobertura").value(hasItem(DEFAULT_COBERTURA)))
            .andExpect(jsonPath("$.[*].produto").value(hasItem(DEFAULT_PRODUTO)))
            .andExpect(jsonPath("$.[*].dataInicioVigencia").value(hasItem(DEFAULT_DATA_INICIO_VIGENCIA.toString())))
            .andExpect(jsonPath("$.[*].dataFimVigencia").value(hasItem(DEFAULT_DATA_FIM_VIGENCIA.toString())));
    }

    @Test
    public void getProdutoCobertura() throws Exception {
        // Initialize the database
        produtoCoberturaRepository.save(produtoCobertura);

        // Get the produtoCobertura
        restProdutoCoberturaMockMvc.perform(get("/api/produto-coberturas/{id}", produtoCobertura.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produtoCobertura.getId()))
            .andExpect(jsonPath("$.cobertura").value(DEFAULT_COBERTURA))
            .andExpect(jsonPath("$.produto").value(DEFAULT_PRODUTO))
            .andExpect(jsonPath("$.dataInicioVigencia").value(DEFAULT_DATA_INICIO_VIGENCIA.toString()))
            .andExpect(jsonPath("$.dataFimVigencia").value(DEFAULT_DATA_FIM_VIGENCIA.toString()));
    }

    @Test
    public void getNonExistingProdutoCobertura() throws Exception {
        // Get the produtoCobertura
        restProdutoCoberturaMockMvc.perform(get("/api/produto-coberturas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateProdutoCobertura() throws Exception {
        // Initialize the database
        produtoCoberturaService.save(produtoCobertura);

        int databaseSizeBeforeUpdate = produtoCoberturaRepository.findAll().size();

        // Update the produtoCobertura
        ProdutoCobertura updatedProdutoCobertura = produtoCoberturaRepository.findOne(produtoCobertura.getId());
        updatedProdutoCobertura
            .cobertura(UPDATED_COBERTURA)
            .produto(UPDATED_PRODUTO)
            .dataInicioVigencia(UPDATED_DATA_INICIO_VIGENCIA)
            .dataFimVigencia(UPDATED_DATA_FIM_VIGENCIA);

        restProdutoCoberturaMockMvc.perform(put("/api/produto-coberturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProdutoCobertura)))
            .andExpect(status().isOk());

        // Validate the ProdutoCobertura in the database
        List<ProdutoCobertura> produtoCoberturaList = produtoCoberturaRepository.findAll();
        assertThat(produtoCoberturaList).hasSize(databaseSizeBeforeUpdate);
        ProdutoCobertura testProdutoCobertura = produtoCoberturaList.get(produtoCoberturaList.size() - 1);
        assertThat(testProdutoCobertura.getCobertura()).isEqualTo(UPDATED_COBERTURA);
        assertThat(testProdutoCobertura.getProduto()).isEqualTo(UPDATED_PRODUTO);
        assertThat(testProdutoCobertura.getDataInicioVigencia()).isEqualTo(UPDATED_DATA_INICIO_VIGENCIA);
        assertThat(testProdutoCobertura.getDataFimVigencia()).isEqualTo(UPDATED_DATA_FIM_VIGENCIA);
    }

    @Test
    public void updateNonExistingProdutoCobertura() throws Exception {
        int databaseSizeBeforeUpdate = produtoCoberturaRepository.findAll().size();

        // Create the ProdutoCobertura

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProdutoCoberturaMockMvc.perform(put("/api/produto-coberturas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produtoCobertura)))
            .andExpect(status().isCreated());

        // Validate the ProdutoCobertura in the database
        List<ProdutoCobertura> produtoCoberturaList = produtoCoberturaRepository.findAll();
        assertThat(produtoCoberturaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteProdutoCobertura() throws Exception {
        // Initialize the database
        produtoCoberturaService.save(produtoCobertura);

        int databaseSizeBeforeDelete = produtoCoberturaRepository.findAll().size();

        // Get the produtoCobertura
        restProdutoCoberturaMockMvc.perform(delete("/api/produto-coberturas/{id}", produtoCobertura.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProdutoCobertura> produtoCoberturaList = produtoCoberturaRepository.findAll();
        assertThat(produtoCoberturaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProdutoCobertura.class);
        ProdutoCobertura produtoCobertura1 = new ProdutoCobertura();
        produtoCobertura1.setId("id1");
        ProdutoCobertura produtoCobertura2 = new ProdutoCobertura();
        produtoCobertura2.setId(produtoCobertura1.getId());
        assertThat(produtoCobertura1).isEqualTo(produtoCobertura2);
        produtoCobertura2.setId("id2");
        assertThat(produtoCobertura1).isNotEqualTo(produtoCobertura2);
        produtoCobertura1.setId(null);
        assertThat(produtoCobertura1).isNotEqualTo(produtoCobertura2);
    }
}
