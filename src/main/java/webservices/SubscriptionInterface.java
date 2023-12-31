package webservices;

import models.Subscription;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SubscriptionInterface {
    @WebMethod
    public Subscription subscribe(int user_id, int album_id, String ipAddress);

    @WebMethod
    public void unsubscribe(int user_id, int album_id, String ipAddress);

    @WebMethod
    public void notifySubscriber(int album_id, String album_name, String ipAddress);

    @WebMethod
    public void removeSubscriptionByAlbumId(int album_id, String ipAdress);
}