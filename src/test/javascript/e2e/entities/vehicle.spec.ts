import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Vehicle e2e test', () => {

    let navBarPage: NavBarPage;
    let vehicleDialogPage: VehicleDialogPage;
    let vehicleComponentsPage: VehicleComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Vehicles', () => {
        navBarPage.goToEntity('vehicle');
        vehicleComponentsPage = new VehicleComponentsPage();
        expect(vehicleComponentsPage.getTitle()).toMatch(/Vehicles/);

    });

    it('should load create Vehicle dialog', () => {
        vehicleComponentsPage.clickOnCreateButton();
        vehicleDialogPage = new VehicleDialogPage();
        expect(vehicleDialogPage.getModalTitle()).toMatch(/Create or edit a Vehicle/);
        vehicleDialogPage.close();
    });

    it('should create and save Vehicles', () => {
        vehicleComponentsPage.clickOnCreateButton();
        vehicleDialogPage.manufacturerSelectLastOption();
        vehicleDialogPage.setModelInput('model');
        expect(vehicleDialogPage.getModelInput()).toMatch('model');
        vehicleDialogPage.setUrlInput('url');
        expect(vehicleDialogPage.getUrlInput()).toMatch('url');
        vehicleDialogPage.save();
        expect(vehicleDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class VehicleComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-vehicle div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class VehicleDialogPage {
    modalTitle = element(by.css('h4#myVehicleLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    manufacturerSelect = element(by.css('select#field_manufacturer'));
    modelInput = element(by.css('input#field_model'));
    urlInput = element(by.css('input#field_url'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setManufacturerSelect = function (manufacturer) {
        this.manufacturerSelect.sendKeys(manufacturer);
    }

    getManufacturerSelect = function () {
        return this.manufacturerSelect.element(by.css('option:checked')).getText();
    }

    manufacturerSelectLastOption = function () {
        this.manufacturerSelect.all(by.tagName('option')).last().click();
    }
    setModelInput = function (model) {
        this.modelInput.sendKeys(model);
    }

    getModelInput = function () {
        return this.modelInput.getAttribute('value');
    }

    setUrlInput = function (url) {
        this.urlInput.sendKeys(url);
    }

    getUrlInput = function () {
        return this.urlInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
