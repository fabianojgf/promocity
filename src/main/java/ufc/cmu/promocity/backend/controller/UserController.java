package ufc.cmu.promocity.backend.controller;

import ufc.cmu.promocity.backend.context.PromotionArea;
import ufc.cmu.promocity.backend.context.UserLocationMonitoring;
import ufc.cmu.promocity.backend.model.Coupon;
import ufc.cmu.promocity.backend.model.Promotion;
import ufc.cmu.promocity.backend.model.Store;
import ufc.cmu.promocity.backend.model.Track;
import ufc.cmu.promocity.backend.model.User;
import ufc.cmu.promocity.backend.report.ReportCoupon;
import ufc.cmu.promocity.backend.service.CouponsService;
import ufc.cmu.promocity.backend.service.PromotionsService;
import ufc.cmu.promocity.backend.service.StoreService;
import ufc.cmu.promocity.backend.service.TrackService;
import ufc.cmu.promocity.backend.service.UsersService;
import ufc.cmu.promocity.backend.utils.GeradorSenha;
import ufc.cmu.promocity.backend.utils.ManipuladorDatas;
import ufc.cmu.promocity.backend.utils.Message;

import java.net.URI;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.CustomSQLExceptionTranslatorRegistrar;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * Users Controller
 * @author armandosoaressousa
 *
 */
@Component
@Path("/users")
@Transactional
public class UserController {
	private UsersService userService;
	private StoreService storeService;	
	private CouponsService couponService;
	private TrackService trackService;
	private PromotionsService promotionService;
	
	private UserLocationMonitoring userLocationMonitoring;
	public PromotionArea globalPromotionArea;

	@Autowired
	public void setTrackService(TrackService trackService) {
		this.trackService = trackService;
	}
	
	@Autowired
	public void setCouponService(CouponsService couponServices){
		this.couponService = couponServices;
	}
	
	@Autowired
	public void setUserService(UsersService userServices){
		this.userService = userServices;
	}
	
	@Autowired
	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}
	
	@Autowired
	public void setPromotionService(PromotionsService promotionService) {
		this.promotionService = promotionService;
	}

	/**
	 * Contrutor of UserController
	 * @param userService
	 */
	public UserController() {
	    this.userLocationMonitoring = new UserLocationMonitoring(globalPromotionArea);	   
	}

	 /**
     * Retorna em um JSON todos os usuarios cadastrados
     * @return código http
     */
    @GET
    @Produces("application/json")
    public List<User> getAllUsers() {
       	List<User> listUsers = new LinkedList<User>();
    	listUsers = userService.getListAll();
    	return listUsers;
    }
    
    /**
     * Dado um id retorna o JSON dos dados do usuario
     * @param id
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/{id}")
    public User getUser(@PathParam("id") String id) {
    	return userService.get(Long.parseLong(id));
    }
    
    /**
     * Dados os dados de um usuario adiciona um usuario no repositorio
     * @param user
     * @return código http
     */
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addUser(User user) {
        userService.save(user);
        URI uri = URI.create("/" + String.valueOf(user.getId()));
		return Response.created(uri).build();
    }
    
    /**
     * Dado um id e os dados do user faz sua atualizacao
     * @param id
     * @param user
     * @return código http
     */
    @PUT
    @Consumes("application/json")
    @Path("/{id}")
    public Response updateUser(@PathParam("id") String id, User user) {
       userService.save(user);
       return Response.noContent().build();
    }

    /**
     * ../1/monitoring/location/0/0
     * exemplo dados enviados em forma de json: 
     * {
  		"id": 1,
  		"latitude": 0,
  		"longitude": 0
		}
     * Dado um id, latitude e longitude de um usuário envia sua localização instantanea
     * @param id
     * @param latitude
     * @param longitude
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("{id}/monitoring/location/{latitude}/{longitude}")
    public List<ReportCoupon> monitoringUserLocation(@PathParam("id") String id, @PathParam("latitude") String latitude, @PathParam("longitude") String longitude) {
    	List<Coupon> couponList = new LinkedList<Coupon>();
    	List<ReportCoupon> cuponsDetalhados = new LinkedList<ReportCoupon>();
    		
    	User user = userService.get(Long.parseLong(id));
				
    	Track position = new Track();
    	position.setUser(user);
    	Date date = new ManipuladorDatas().getCurrentDate();
    	
    	position.setLatitude(Double.valueOf(latitude));
    	position.setLongitude(Double.valueOf(longitude));
    	position.setDate(date);
    	this.trackService.save(position);
    	
    	user.setLatitude(Double.valueOf(latitude));
    	user.setLongitude(Double.valueOf(longitude));
    	
	    this.globalPromotionArea = PromotionArea.getInstance();
	    this.globalPromotionArea.setStoreAreasRegistered(this.storeService.getListAll());
	    this.userLocationMonitoring.setPromotionArea(globalPromotionArea);
    	
	    userLocationMonitoring.checkUserContext(user);
        
	    couponList = userLocationMonitoring.getListaDeCuponsColetados();
	    cuponsDetalhados = userLocationMonitoring.getListaDeReportCupomColetados();
	            
        if (couponList.size() > 0) {
        	for (Coupon cupom : couponList) {
        		user.addCoupon(cupom);
        	}        	
            userService.save(user);
            return cuponsDetalhados;
        }
        else {
        	return null;
        }        
    }

    /**
     * Dado um id de um usuario faz sua remocao do repositorio
     * @param id
     * @return código http
     */
    @DELETE
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") String id) {
        userService.delete(Long.parseLong(id));
        return Response.ok().build();
    }
    
    /**
     * Get all coupons from specific idUser
		users/id/coupons
 
     * Retorna em um JSON todas os cupons de um dado user cadastrado
     * id do user
     * @return lista de cupons de um usuario
     */
    @GET
    @Produces("application/json")
    @Path("/{id}/coupons")
    public List<Coupon> getAllCouponsfromUser(@PathParam("id") String id) {
        return userService.get(Long.parseLong(id)).getCoupons();
    }
    
    /**
     * Get all coupons from specific idUser
		users/id/coupons
 
     * Retorna em um JSON todas os cupons de um dado user cadastrado
     * id do user
     * @return lista de cupons de um usuario
     */
    @GET
    @Produces("application/json")
    @Path("/{idUser}/catchCouponsInLocation/{latitude}/{longitude}")
    public List<Coupon> catchNearCoupons(@PathParam("idUser") String idUser, 
    		@PathParam("latitude") String latitude, 
    		@PathParam("longitude") String longitude) {
    	
    	User user = userService.get(Long.parseLong(idUser));
    	double latitudeValue = Double.parseDouble(latitude);
    	double longitudeValue = Double.parseDouble(longitude);
    	
    	/*Salbando a localizacão do usuário*/
    	user.setLatitude(latitudeValue);
    	user.setLongitude(longitudeValue);
    	userService.save(user);
    	
    	Track track = new Track();
    	track.setDate(new Date());
    	track.setLatitude(latitudeValue);
    	track.setLongitude(longitudeValue);
    	track.setUser(user);
    	
    	trackService.save(track);
    	
    	/*Coletando os cupons de acordo com a localizacão do usuário*/
    	
    	List<Promotion> nearPromtions = promotionService.findInsideRadius(latitudeValue, longitudeValue);
    	
    	Iterator<Promotion> it = nearPromtions.iterator();
    	while(it.hasNext()) {
    		Promotion promotion = it.next();
    		
    		if(promotion.hasAvailableCoupons() 
    				&& !user.alreadyCouponPromotion(promotion)) {
    			Coupon coupon = new Coupon();
    			coupon.setUser(user);
    			coupon.setPromotion(promotion);
    			coupon.setNumRequiredCoUsers(promotion.getNumRequiredCoUsers());
    			couponService.save(coupon);
    			
    			coupon.updateQrCode();
    	        couponService.save(coupon);
    			
    			promotion.setNumReleasedCoupons(promotion.getNumReleasedCoupons() + 1);
    			promotionService.save(promotion);
    		}
    	}
    	
        return userService.get(Long.parseLong(idUser)).getCoupons();
    }
    
    @GET
    @Produces("application/json")
    @Path("/{idUser}/updateLocation/{latitude}/{longitude}")
    public Response updateUserLocation(@PathParam("idUser") String idUser, 
    		@PathParam("latitude") String latitude, 
    		@PathParam("longitude") String longitude) {
    	User user = userService.get(Long.parseLong(idUser));
    	double latitudeValue = Double.parseDouble(latitude);
    	double longitudeValue = Double.parseDouble(longitude);
    	
    	/*Salbando a localizacão do usuário*/
    	user.setLatitude(latitudeValue);
    	user.setLongitude(longitudeValue);
    	userService.save(user);
    	
    	Track track = new Track();
    	track.setDate(new Date());
    	track.setLatitude(latitudeValue);
    	track.setLongitude(longitudeValue);
    	track.setUser(user);
    	trackService.save(track);
    	
    	return Response.ok().build();
    }

    /**
     * Get a specific coupon from specific user
	users/idUser/coupons/idCoupon

     * Return data from coupon in user
     * @param idUser 
     * @param idPCoupon
     * @return dados do coupon especifico
     */
    @GET
    @Produces("application/json")
    @Path("/{idUser}/promotions/{idCoupon}")
    public Coupon getCouponFromUser(@PathParam("idUser") String idUser, @PathParam("idCoupon") String idCoupon) {
    	User user = userService.get(Long.parseLong(idUser));
    	
    	for (Coupon element : user.getCoupons()) {
    		if (element.getId() == Long.parseLong(idCoupon)) {
    			return element;
    		}
    	}
    	
    	return null;
    }
    
    /**
     * Dado email e senha retorna o JSON dos dados do usuario
     * @param 
     * @return código http
     */
    @GET
    @Produces("application/json")
    @Path("/{email}/{senha}")
    public Object getUserAutenticado(@PathParam("email") String email,@PathParam("senha") String senha) {
    	User user = userService.getUserByEmail(email);
    	Message message = new Message();
    	
    	//consulta o usuário por email e se existe retorna os dados do usuário
    	if (user != null) { //usuário existe
    		boolean checaSenha = new GeradorSenha().comparaSenhas(senha, user.getPassword());
        	if (senha.length() >0 && checaSenha){        		
                return user;	
        	}else{        		
        		message.setId(1);
        		message.setConteudo("Senha incorreta!");
                return message;	    		
        	}    	
    		
    	}else {
    		message.setId(2);
    		message.setConteudo("Usuário não existe!");
    		return message;
    	}    	
    }

    /**
     * Adiciona um amigo de forma birecional
     * @param idUser
     * @param idFriend
     * @return
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/add/friend/{idFriend}")
    public Object addFriend(@PathParam("idUser") long idUser, @PathParam("idFriend") long idFriend) {
    	Message message = new Message();
    	
    	User user = this.userService.get(idUser);
    	User friend = this.userService.get(idFriend);
    	
    	if (user.addFriend(friend)) {
    		this.userService.save(user);
    		if (friend.addFriend(user)){
    			this.userService.save(friend);	
    		}    
    		message.setId(3);
    		message.setConteudo("O amigo foi salvo com sucesso.");
    	}else {
    		message.setId(4);
    		message.setConteudo("O amigo já existe!!!!.");
    	}
    	
    	return message;	
    }
    
    /**
     * Dado um usuário logado lista os amigos dele
     * @param idUser
     * @param model
     * @return
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/list/friends")
    public List<User> listFriends(@PathParam("idUser") long idUser) {    

		User user = this.userService.get(idUser);
		List<User> idFriends = user.getFriends();
		
		List<User> listaAmigos = new LinkedList<User>();
		
		for (User id : idFriends) {
			listaAmigos.add(this.userService.get(id.getId()));
		}
        
        return listaAmigos;
    }
    
    /**
     * Dado um usuário logado, ele remove o amigo selecionado
     * @param idUser
     * @param idFriend
     * @param model
     * @param ra
     * @return
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/delete/friend/{idFriend}")
    public Object deleteFriend(@PathParam("idUser") long idUser, @PathParam("idFriend") long idFriend) {
    	Message message = new Message();
    	
    	User user = this.userService.get(idUser);
    	User friend = this.userService.get(idFriend);
    	
    	if (user.deleteFriend(friend)) {        	 
        	this.userService.save(user);
        	if(friend.deleteFriend(user)) {
        		this.userService.save(friend);
        	}
        	message.setId(5);
        	message.setConteudo("Amigo removido com sucesso!");
    	}else {
        	message.setId(6);
        	message.setConteudo("O amigo não foi removido.");
    	}
    	
    	return message;
    }
    
    /**
     * Ativa um cupom para os 3 amigos que tiverem o mesmo cupom e estivem na proximidade da loja
     * @param idUser
     * @param idCoupon
     * @param idStore
     * @param idFriend1
     * @param idFriend2
     * @return
     */
    @GET
    @Produces("application/json")
    @Path(value="/{idUser}/activate/coupon/{idCoupon}/store/{idStore}/friends/{idFriend1}/{idFriend2}")
    public Object activeCouponFriends(@PathParam("idUser") long idUser, @PathParam("idCoupon") long idCoupon, @PathParam("idStore") long idStore, 
    		@PathParam("idFriend1") long idFriend1, @PathParam("idFriend2") long idFriend2) {
    	
    	User user = this.userService.get(idUser);
    	User friend1 = this.userService.get(idFriend1);
    	User friend2 = this.userService.get(idFriend2);
    	Store store = this.storeService.get(idStore);
    	Coupon myCoupon = this.couponService.get(idCoupon);
    	    	
    	return checkRulesActivateCoupon(user, friend1, friend2, store, myCoupon);
    }

    /**
     * Checa as regras de validação de cupom por 3 amigos
     * @param message
     * @param user
     * @param friend1
     * @param friend2
     * @param store
     * @param myCoupon
     * @return
     */
	private Object checkRulesActivateCoupon(User user, User friend1, User friend2, Store store, Coupon myCoupon) {
		Message message = new Message();
		List<User> custumersAward = new LinkedList<User>();
		
		//1. Checa validade do cupom idCoupon
    	if (myCoupon.isValidCoupon()) {
    		//2. Checa se Friend1 e Friend2 são amigos de user
    		//3. Checa se Friend1 tem Coupon
    		//4. Checa se Friend1 está na vizinhança de Store	
    		//5. Checa se Friend2 tem Coupon			
    		//6. Checa se Friend2 está na vizinhança de Store
    		if (user.alreadyFriend(friend1) && user.alreadyFriend(friend2) &&
    				friend1.alreadyCoupon(myCoupon) && isUserNearByStore(friend1, store) && 
    				friend2.alreadyCoupon(myCoupon) && isUserNearByStore(friend2, store)) {
        		//7. Ativa idCoupon para User, Friend1 e Friend2 com o dobro do desconto original
    			custumersAward.add(user);
    			custumersAward.add(friend1);
    			custumersAward.add(friend2);
    			//8. O cupom fica exclusivo dos amigos que ativaram o cupom e não pode ser usado por outras pessoas.
    			myCoupon.setActivated(true);   			
    			this.couponService.save(myCoupon);
        		message.setId(7);
        		message.setConteudo("Você e seus amigos " + friend1.getId() + ", " + friend2.getId() + ", ativaram o cupom " + myCoupon.getId() +" com sucesso!");
    		}else {
        		message.setId(8);
        		message.setConteudo("Seus amigos " + friend1.getId() + ", " + friend2.getId() + ", não estão na proximidade necessária para ativar o cupom");
    		}
    	}else {
    		message.setId(9);
    		message.setConteudo("O coupon "+ myCoupon.getId() + " não é mais válido." );
    	}
    	return message;
	}
 
	/**
     * Dado um usuário logado lista os amigos dele
     * @param idUser
     * @param model
     * @return
     */
    @GET
    @Produces("application/json")
    @Path(value = "/{idUser}/list/tracks")
    public List<Track> listTrackingUser(@PathParam("idUser") long idUser) {    
		User user = this.userService.get(idUser);		
		List<Track> lista = user.getTracks();	
		
        return lista;
    }
	
    /**
     * Checa se o usuário está nas proximidades da loja dada
     * @param idUser
     * @param idStore
     * @return
     */
    public boolean isUserNearByStore(User user, Store store) {
    	boolean valid=false;    	
    	double distance = new UserLocationMonitoring(null).checkDistanceFromStore(user, store);
    	double radius = new UserLocationMonitoring(null).getRadius();
    	
    	if (distance <= radius) {
    		valid = true;
    	}else {
    		valid = false;
    	}
    	
    	return valid;
    }        
    
}