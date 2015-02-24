package minium.cucumber.rest;

import gherkin.formatter.model.Tag;

import java.util.Collection;

import minium.cucumber.rest.dto.HookDefinitionDTO;
import cucumber.api.Scenario;
import cucumber.runtime.HookDefinition;

public class RemoteHookDefinition implements HookDefinition {

    private transient RemoteBackend remoteBackend;
    private HookDefinitionDTO definitionDto;

    public RemoteHookDefinition(RemoteBackend remoteBackend, HookDefinitionDTO definitionDto) {
        this.remoteBackend = remoteBackend;
        this.definitionDto = definitionDto;
    }


    @Override
    public void execute(Scenario scenario) throws Throwable {
        remoteBackend.execute(definitionDto, scenario);
    }

    @Override
    public String getLocation(boolean detail) {
        return detail ? definitionDto.getDetailedLocation() : definitionDto.getLocation();
    }

    @Override
    public int getOrder() {
        return definitionDto.getOrder();
    }

    @Override
    public boolean matches(Collection<Tag> tags) {
        return remoteBackend.matches(definitionDto, tags);
    }


    @Override
    public boolean isScenarioScoped() {
        // Minium Cucumber REST does not support scenario scoped hooks
        return false;
    }
}