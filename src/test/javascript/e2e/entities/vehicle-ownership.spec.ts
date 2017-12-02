import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';


describe('VehicleOwnership e2e test', () => {

    let navBarPage: NavBarPage;
    let vehicleOwnershipDialogPage: VehicleOwnershipDialogPage;
    let vehicleOwnershipComponentsPage: VehicleOwnershipComponentsPage;


    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load VehicleOwnerships', () => {
        navBarPage.goToEntity('vehicle-ownership');
        vehicleOwnershipComponentsPage = new VehicleOwnershipComponentsPage();
        expect(vehicleOwnershipComponentsPage.getTitle()).toMatch(/Vehicle Ownerships/);

    });

    it('should load create VehicleOwnership dialog', () => {
        vehicleOwnershipComponentsPage.clickOnCreateButton();
        vehicleOwnershipDialogPage = new VehicleOwnershipDialogPage();
        expect(vehicleOwnershipDialogPage.getModalTitle()).toMatch(/Create or edit a Vehicle Ownership/);
        vehicleOwnershipDialogPage.close();
    });

   /* it('should create and save VehicleOwnerships', () => {
        vehicleOwnershipComponentsPage.clickOnCreateButton();
        vehicleOwnershipDialogPage.vehicleSelectLastOption();
        vehicleOwnershipDialogPage.ownerSelectLastOption();
        vehicleOwnershipDialogPage.save();
        expect(vehicleOwnershipDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); */

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class VehicleOwnershipComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-vehicle-ownership div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class VehicleOwnershipDialogPage {
    modalTitle = element(by.css('h4#myVehicleOwnershipLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    vehicleSelect = element(by.css('select#field_vehicle'));
    ownerSelect = element(by.css('select#field_owner'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    vehicleSelectLastOption = function () {
        this.vehicleSelect.all(by.tagName('option')).last().click();
    }

    vehicleSelectOption = function (option) {
        this.vehicleSelect.sendKeys(option);
    }

    getVehicleSelect = function () {
        return this.vehicleSelect;
    }

    getVehicleSelectedOption = function () {
        return this.vehicleSelect.element(by.css('option:checked')).getText();
    }

    ownerSelectLastOption = function () {
        this.ownerSelect.all(by.tagName('option')).last().click();
    }

    ownerSelectOption = function (option) {
        this.ownerSelect.sendKeys(option);
    }

    getOwnerSelect = function () {
        return this.ownerSelect;
    }

    getOwnerSelectedOption = function () {
        return this.ownerSelect.element(by.css('option:checked')).getText();
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
