package org.beyond_infinity.app.web.rest;

import org.beyond_infinity.app.BeyondInfinityApp;

import org.beyond_infinity.app.domain.VehicleOwnership;
import org.beyond_infinity.app.domain.Vehicle;
import org.beyond_infinity.app.domain.User;
import org.beyond_infinity.app.repository.VehicleOwnershipRepository;
import org.beyond_infinity.app.service.VehicleOwnershipService;
import org.beyond_infinity.app.service.dto.VehicleOwnershipDTO;
import org.beyond_infinity.app.service.mapper.VehicleOwnershipMapper;
import org.beyond_infinity.app.web.rest.errors.ExceptionTranslator;
import org.beyond_infinity.app.service.dto.VehicleOwnershipCriteria;
import org.beyond_infinity.app.service.VehicleOwnershipQueryService;

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

/**
 * Test class for the VehicleOwnershipResource REST controller.
 *
 * @see VehicleOwnershipResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeyondInfinityApp.class)
public class VehicleOwnershipResourceIntTest {

    @Autowired
    private VehicleOwnershipRepository vehicleOwnershipRepository;

    @Autowired
    private VehicleOwnershipMapper vehicleOwnershipMapper;

    @Autowired
    private VehicleOwnershipService vehicleOwnershipService;

    @Autowired
    private VehicleOwnershipQueryService vehicleOwnershipQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVehicleOwnershipMockMvc;

    private VehicleOwnership vehicleOwnership;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VehicleOwnershipResource vehicleOwnershipResource = new VehicleOwnershipResource(vehicleOwnershipService, vehicleOwnershipQueryService);
        this.restVehicleOwnershipMockMvc = MockMvcBuilders.standaloneSetup(vehicleOwnershipResource)
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
    public static VehicleOwnership createEntity(EntityManager em) {
        VehicleOwnership vehicleOwnership = new VehicleOwnership();
        // Add required entity
        Vehicle vehicle = VehicleResourceIntTest.createEntity(em);
        em.persist(vehicle);
        em.flush();
        vehicleOwnership.setVehicle(vehicle);
        // Add required entity
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        vehicleOwnership.setOwner(owner);
        return vehicleOwnership;
    }

    @Before
    public void initTest() {
        vehicleOwnership = createEntity(em);
    }

    @Test
    @Transactional
    public void createVehicleOwnership() throws Exception {
        int databaseSizeBeforeCreate = vehicleOwnershipRepository.findAll().size();

        // Create the VehicleOwnership
        VehicleOwnershipDTO vehicleOwnershipDTO = vehicleOwnershipMapper.toDto(vehicleOwnership);
        restVehicleOwnershipMockMvc.perform(post("/api/vehicle-ownerships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleOwnershipDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleOwnership in the database
        List<VehicleOwnership> vehicleOwnershipList = vehicleOwnershipRepository.findAll();
        assertThat(vehicleOwnershipList).hasSize(databaseSizeBeforeCreate + 1);
        VehicleOwnership testVehicleOwnership = vehicleOwnershipList.get(vehicleOwnershipList.size() - 1);
    }

    @Test
    @Transactional
    public void createVehicleOwnershipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vehicleOwnershipRepository.findAll().size();

        // Create the VehicleOwnership with an existing ID
        vehicleOwnership.setId(1L);
        VehicleOwnershipDTO vehicleOwnershipDTO = vehicleOwnershipMapper.toDto(vehicleOwnership);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVehicleOwnershipMockMvc.perform(post("/api/vehicle-ownerships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleOwnershipDTO)))
            .andExpect(status().isBadRequest());

        // Validate the VehicleOwnership in the database
        List<VehicleOwnership> vehicleOwnershipList = vehicleOwnershipRepository.findAll();
        assertThat(vehicleOwnershipList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllVehicleOwnerships() throws Exception {
        // Initialize the database
        vehicleOwnershipRepository.saveAndFlush(vehicleOwnership);

        // Get all the vehicleOwnershipList
        restVehicleOwnershipMockMvc.perform(get("/api/vehicle-ownerships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleOwnership.getId().intValue())));
    }

    @Test
    @Transactional
    public void getVehicleOwnership() throws Exception {
        // Initialize the database
        vehicleOwnershipRepository.saveAndFlush(vehicleOwnership);

        // Get the vehicleOwnership
        restVehicleOwnershipMockMvc.perform(get("/api/vehicle-ownerships/{id}", vehicleOwnership.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vehicleOwnership.getId().intValue()));
    }

    @Test
    @Transactional
    public void getAllVehicleOwnershipsByVehicleIsEqualToSomething() throws Exception {
        // Initialize the database
        Vehicle vehicle = VehicleResourceIntTest.createEntity(em);
        em.persist(vehicle);
        em.flush();
        vehicleOwnership.setVehicle(vehicle);
        vehicleOwnershipRepository.saveAndFlush(vehicleOwnership);
        Long vehicleId = vehicle.getId();

        // Get all the vehicleOwnershipList where vehicle equals to vehicleId
        defaultVehicleOwnershipShouldBeFound("vehicleId.equals=" + vehicleId);

        // Get all the vehicleOwnershipList where vehicle equals to vehicleId + 1
        defaultVehicleOwnershipShouldNotBeFound("vehicleId.equals=" + (vehicleId + 1));
    }


    @Test
    @Transactional
    public void getAllVehicleOwnershipsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        User owner = UserResourceIntTest.createEntity(em);
        em.persist(owner);
        em.flush();
        vehicleOwnership.setOwner(owner);
        vehicleOwnershipRepository.saveAndFlush(vehicleOwnership);
        Long ownerId = owner.getId();

        // Get all the vehicleOwnershipList where owner equals to ownerId
        defaultVehicleOwnershipShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the vehicleOwnershipList where owner equals to ownerId + 1
        defaultVehicleOwnershipShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultVehicleOwnershipShouldBeFound(String filter) throws Exception {
        restVehicleOwnershipMockMvc.perform(get("/api/vehicle-ownerships?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vehicleOwnership.getId().intValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultVehicleOwnershipShouldNotBeFound(String filter) throws Exception {
        restVehicleOwnershipMockMvc.perform(get("/api/vehicle-ownerships?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingVehicleOwnership() throws Exception {
        // Get the vehicleOwnership
        restVehicleOwnershipMockMvc.perform(get("/api/vehicle-ownerships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVehicleOwnership() throws Exception {
        // Initialize the database
        vehicleOwnershipRepository.saveAndFlush(vehicleOwnership);
        int databaseSizeBeforeUpdate = vehicleOwnershipRepository.findAll().size();

        // Update the vehicleOwnership
        VehicleOwnership updatedVehicleOwnership = vehicleOwnershipRepository.findOne(vehicleOwnership.getId());
        // Disconnect from session so that the updates on updatedVehicleOwnership are not directly saved in db
        em.detach(updatedVehicleOwnership);
        VehicleOwnershipDTO vehicleOwnershipDTO = vehicleOwnershipMapper.toDto(updatedVehicleOwnership);

        restVehicleOwnershipMockMvc.perform(put("/api/vehicle-ownerships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleOwnershipDTO)))
            .andExpect(status().isOk());

        // Validate the VehicleOwnership in the database
        List<VehicleOwnership> vehicleOwnershipList = vehicleOwnershipRepository.findAll();
        assertThat(vehicleOwnershipList).hasSize(databaseSizeBeforeUpdate);
        VehicleOwnership testVehicleOwnership = vehicleOwnershipList.get(vehicleOwnershipList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingVehicleOwnership() throws Exception {
        int databaseSizeBeforeUpdate = vehicleOwnershipRepository.findAll().size();

        // Create the VehicleOwnership
        VehicleOwnershipDTO vehicleOwnershipDTO = vehicleOwnershipMapper.toDto(vehicleOwnership);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVehicleOwnershipMockMvc.perform(put("/api/vehicle-ownerships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vehicleOwnershipDTO)))
            .andExpect(status().isCreated());

        // Validate the VehicleOwnership in the database
        List<VehicleOwnership> vehicleOwnershipList = vehicleOwnershipRepository.findAll();
        assertThat(vehicleOwnershipList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVehicleOwnership() throws Exception {
        // Initialize the database
        vehicleOwnershipRepository.saveAndFlush(vehicleOwnership);
        int databaseSizeBeforeDelete = vehicleOwnershipRepository.findAll().size();

        // Get the vehicleOwnership
        restVehicleOwnershipMockMvc.perform(delete("/api/vehicle-ownerships/{id}", vehicleOwnership.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VehicleOwnership> vehicleOwnershipList = vehicleOwnershipRepository.findAll();
        assertThat(vehicleOwnershipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleOwnership.class);
        VehicleOwnership vehicleOwnership1 = new VehicleOwnership();
        vehicleOwnership1.setId(1L);
        VehicleOwnership vehicleOwnership2 = new VehicleOwnership();
        vehicleOwnership2.setId(vehicleOwnership1.getId());
        assertThat(vehicleOwnership1).isEqualTo(vehicleOwnership2);
        vehicleOwnership2.setId(2L);
        assertThat(vehicleOwnership1).isNotEqualTo(vehicleOwnership2);
        vehicleOwnership1.setId(null);
        assertThat(vehicleOwnership1).isNotEqualTo(vehicleOwnership2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VehicleOwnershipDTO.class);
        VehicleOwnershipDTO vehicleOwnershipDTO1 = new VehicleOwnershipDTO();
        vehicleOwnershipDTO1.setId(1L);
        VehicleOwnershipDTO vehicleOwnershipDTO2 = new VehicleOwnershipDTO();
        assertThat(vehicleOwnershipDTO1).isNotEqualTo(vehicleOwnershipDTO2);
        vehicleOwnershipDTO2.setId(vehicleOwnershipDTO1.getId());
        assertThat(vehicleOwnershipDTO1).isEqualTo(vehicleOwnershipDTO2);
        vehicleOwnershipDTO2.setId(2L);
        assertThat(vehicleOwnershipDTO1).isNotEqualTo(vehicleOwnershipDTO2);
        vehicleOwnershipDTO1.setId(null);
        assertThat(vehicleOwnershipDTO1).isNotEqualTo(vehicleOwnershipDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(vehicleOwnershipMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(vehicleOwnershipMapper.fromId(null)).isNull();
    }
}
