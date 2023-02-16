package nl.suriani.validation.exercise.domain.sensor;

import nl.suriani.validation.exercise.domain.shared.Guards;

public record Firmware(FirmwareVersion version) {

    public Firmware {
        Guards.isRequired(version);
    }
}
