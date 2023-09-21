package org.apdb4j.core.permissions.account;

import lombok.NonNull;
import org.apdb4j.core.permissions.AbstractPermission;
import org.apdb4j.core.permissions.AccessType;
import org.apdb4j.core.permissions.services.PictureAccess;
import org.apdb4j.core.permissions.tickets.TicketAccess;
import org.apdb4j.core.permissions.tickets.TicketTypeAccess;

public class GuestPermission extends AbstractPermission implements AccountAccess, PictureAccess, TicketAccess, TicketTypeAccess {
    @Override
    public @NonNull AccessType getAccessOfEmail() {
        return AccessType.WRITE;
    }

    @Override
    public @NonNull AccessType getAccessOfUsername() {
        return AccessType.WRITE;
    }

    @Override
    public @NonNull AccessType getAccessOfPassword() {
        return AccessType.READ;
    }

    @Override
    public @NonNull AccessType getAccessOfPicturePath() {
        return AccessType.ALL;
    }

    @Override
    public @NonNull AccessType getAccessOfTicketID() {
        return AccessType.NONE;
    }

    @Override
    public @NonNull AccessType getAccessOfTicketPurchaseDate() {
        return AccessType.NONE;
    }

    @Override
    public @NonNull AccessType getAccessOfTicketPunchDate() {
        return AccessType.NONE;
    }

    @Override
    public @NonNull AccessType getAccessOfTicketTypePrice() {
        return AccessType.NONE;
    }

    @Override
    public @NonNull AccessType getAccessOfTicketTypeType() {
        return AccessType.NONE;
    }

    @Override
    public @NonNull AccessType getAccessOfTicketTypeTarget() {
        return AccessType.NONE;
    }

    @Override
    public @NonNull AccessType getAccessOfTicketTypeDuration() {
        return AccessType.NONE;
    }
}
