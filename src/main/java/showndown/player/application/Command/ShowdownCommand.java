package showndown.player.application.Command;

import shared.core.domain.Command.Command;
import shared.core.infrastructure.Controller.AppController;

final public class ShowdownCommand implements Command {

    private final AppController receiver;

    private ShowdownCommand(AppController receiver) {
        this.receiver = receiver;
    }

    public static ShowdownCommand of(AppController receiver) {
        return new ShowdownCommand(receiver);
    }

    public void execute(String... args) {
        receiver.play();
    }
}
