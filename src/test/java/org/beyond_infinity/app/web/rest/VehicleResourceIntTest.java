package org.beyond_infinity.app.web.rest;

import org.beyond_infinity.app.BeyondInfinityApp;

import org.beyond_infinity.app.domain.Vehicle;
import org.beyond_infinity.app.repository.VehicleRepository;
import org.beyond_infinity.app.service.VehicleService;
import org.beyond_infinity.app.service.dto.VehicleDTO;
import org.beyond_infinity.app.service.mapper.VehicleMapper;
import org.beyond_infinity.app.web.rest.errors.ExceptionTranslator;
import org.beyond_infinity.app.service.dto.VehicleCriteria;
import org.beyond_infinity.app.service.VehicleQueryService;

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.beyond_infinity.app.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.beyond_infinity.app.domain.enumeration.Manufacturer;
/**
 * Test class for the VehicleResource REST controller.
 *
 * @see VehicleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeyondInfinityApp.class)
public class VehicleResourceIntTest {

    private static final Manufacturer DEFAULT_MANUFACTURER = Manufacturer.AEGIS_DYNAMICS;
    private static final Manufacturer UPDATED_MANUFACTURER = Manufacturer.ANVIL_AEROSPACE;

    private static final String DEFAULT_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_MODEL = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAAAAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBBBBBBBBBBBB";

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleMapper vehicleMapper;

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleQueryService vehicleQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehicleMockMvc;

    private Vehicle vehicle;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleResource vehicleResource = new VehicleResource(vehicleService, vehicleQueryService);
        this.restVehicleMockMvc = MockMvcBuilders.standaloneSetup(vehicleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vehicle createEntity(EntityManager em) {
        Vehicle vehicle = new Vehicle()
            .manufacturer(DEFAULT_MANUFACTURER)
            .model(DEFAULT_MODEL)
            .url(DEFAULT_URL);
        return vehicle;
    }

    @Before
    public void initTest() {
        vehicle = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicle() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate + 1);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getManufacturer()).isEqualTo(DEFAULT_MANUFACTURER);
        assertThat(testVehicle.getModel()).isEqualTo(DEFAULT_MODEL);
        assertThat(testVehicle.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createVehicleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleRepository.findAll().size();

        // Create the Vehicle with an existing ID
        vehicle.setId(1L);
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkManufacturerIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setManufacturer(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkModelIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setModel(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = vehicleRepository.findAll().size();
        // set the field null
        vehicle.setUrl(null);

        // Create the Vehicle, which fails.
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        restVehicleMockMvc.perform(post("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isBadRequest());

        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVehicles() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", vehicle.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicle.getId().intValue()))
            .andExpect(jsonPath("$.manufacturer").value(DEFAULT_MANUFACTURER.toString()))
            .andExpect(jsonPath("$.model").value(DEFAULT_MODEL.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getAllVehiclesByManufacturerIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where manufacturer equals to DEFAULT_MANUFACTURER
        defaultVehicleShouldBeFound("manufacturer.equals=" + DEFAULT_MANUFACTURER);

        // Get all the vehicleList where manufacturer equals to UPDATED_MANUFACTURER
        defaultVehicleShouldNotBeFound("manufacturer.equals=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByManufacturerIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where manufacturer in DEFAULT_MANUFACTURER or UPDATED_MANUFACTURER
        defaultVehicleShouldBeFound("manufacturer.in=" + DEFAULT_MANUFACTURER + "," + UPDATED_MANUFACTURER);

        // Get all the vehicleList where manufacturer equals to UPDATED_MANUFACTURER
        defaultVehicleShouldNotBeFound("manufacturer.in=" + UPDATED_MANUFACTURER);
    }

    @Test
    @Transactional
    public void getAllVehiclesByManufacturerIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where manufacturer is not null
        defaultVehicleShouldBeFound("manufacturer.specified=true");

        // Get all the vehicleList where manufacturer is null
        defaultVehicleShouldNotBeFound("manufacturer.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByModelIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where model equals to DEFAULT_MODEL
        defaultVehicleShouldBeFound("model.equals=" + DEFAULT_MODEL);

        // Get all the vehicleList where model equals to UPDATED_MODEL
        defaultVehicleShouldNotBeFound("model.equals=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllVehiclesByModelIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where model in DEFAULT_MODEL or UPDATED_MODEL
        defaultVehicleShouldBeFound("model.in=" + DEFAULT_MODEL + "," + UPDATED_MODEL);

        // Get all the vehicleList where model equals to UPDATED_MODEL
        defaultVehicleShouldNotBeFound("model.in=" + UPDATED_MODEL);
    }

    @Test
    @Transactional
    public void getAllVehiclesByModelIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where model is not null
        defaultVehicleShouldBeFound("model.specified=true");

        // Get all the vehicleList where model is null
        defaultVehicleShouldNotBeFound("model.specified=false");
    }

    @Test
    @Transactional
    public void getAllVehiclesByUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where url equals to DEFAULT_URL
        defaultVehicleShouldBeFound("url.equals=" + DEFAULT_URL);

        // Get all the vehicleList where url equals to UPDATED_URL
        defaultVehicleShouldNotBeFound("url.equals=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllVehiclesByUrlIsInShouldWork() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where url in DEFAULT_URL or UPDATED_URL
        defaultVehicleShouldBeFound("url.in=" + DEFAULT_URL + "," + UPDATED_URL);

        // Get all the vehicleList where url equals to UPDATED_URL
        defaultVehicleShouldNotBeFound("url.in=" + UPDATED_URL);
    }

    @Test
    @Transactional
    public void getAllVehiclesByUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);

        // Get all the vehicleList where url is not null
        defaultVehicleShouldBeFound("url.specified=true");

        // Get all the vehicleList where url is null
        defaultVehicleShouldNotBeFound("url.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVehicleShouldBeFound(String filter) throws Exception {
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicle.getId().intValue())))
            .andExpect(jsonPath("$.[*].manufacturer").value(hasItem(DEFAULT_MANUFACTURER.toString())))
            .andExpect(jsonPath("$.[*].model").value(hasItem(DEFAULT_MODEL.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVehicleShouldNotBeFound(String filter) throws Exception {
        restVehicleMockMvc.perform(get("/api/vehicles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingVehicle() throws Exception {
        // Get the vehicle
        restVehicleMockMvc.perform(get("/api/vehicles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Update the vehicle
        Vehicle updatedVehicle = vehicleRepository.findOne(vehicle.getId());
        // Disconnect from session so that the updates on updatedVehicle are not directly saved in db
        em.detach(updatedVehicle);
        updatedVehicle
            .manufacturer(UPDATED_MANUFACTURER)
            .model(UPDATED_MODEL)
            .url(UPDATED_URL);
        VehicleDTO vehicleDTO = vehicleMapper.toDto(updatedVehicle);

        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isOk());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate);
        Vehicle testVehicle = vehicleList.get(vehicleList.size() - 1);
        assertThat(testVehicle.getManufacturer()).isEqualTo(UPDATED_MANUFACTURER);
        assertThat(testVehicle.getModel()).isEqualTo(UPDATED_MODEL);
        assertThat(testVehicle.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicle() throws Exception {
        int databaseSizeBeforeUpdate = vehicleRepository.findAll().size();

        // Create the Vehicle
        VehicleDTO vehicleDTO = vehicleMapper.toDto(vehicle);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehicleMockMvc.perform(put("/api/vehicles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleDTO)))
            .andExpect(status().isCreated());

        // Validate the Vehicle in the database
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehicle() throws Exception {
        // Initialize the database
        vehicleRepository.saveAndFlush(vehicle);
        int databaseSizeBeforeDelete = vehicleRepository.findAll().size();

        // Get the vehicle
        restVehicleMockMvc.perform(delete("/api/vehicles/{id}", vehicle.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Vehicle> vehicleList = vehicleRepository.findAll();
        assertThat(vehicleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vehicle.class);
        Vehicle vehicle1 = new Vehicle();
        vehicle1.setId(1L);
        Vehicle vehicle2 = new Vehicle();
        vehicle2.setId(vehicle1.getId());
        assertThat(vehicle1).isEqualTo(vehicle2);
        vehicle2.setId(2L);
        assertThat(vehicle1).isNotEqualTo(vehicle2);
        vehicle1.setId(null);
        assertThat(vehicle1).isNotEqualTo(vehicle2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleDTO.class);
        VehicleDTO vehicleDTO1 = new VehicleDTO();
        vehicleDTO1.setId(1L);
        VehicleDTO vehicleDTO2 = new VehicleDTO();
        assertThat(vehicleDTO1).isNotEqualTo(vehicleDTO2);
        vehicleDTO2.setId(vehicleDTO1.getId());
        assertThat(vehicleDTO1).isEqualTo(vehicleDTO2);
        vehicleDTO2.setId(2L);
        assertThat(vehicleDTO1).isNotEqualTo(vehicleDTO2);
        vehicleDTO1.setId(null);
        assertThat(vehicleDTO1).isNotEqualTo(vehicleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleMapper.fromId(null)).isNull();
    }
}
