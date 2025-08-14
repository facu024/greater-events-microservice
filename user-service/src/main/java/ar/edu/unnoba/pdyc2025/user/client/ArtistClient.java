package ar.edu.unnoba.pdyc2025.user.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ar.edu.unnoba.pdyc2025.user.dto.ArtistDTO;


@Component
public class ArtistClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String ARTIST_SERVICE_URL = "http://localhost:8081/public/artists";

    public ArtistDTO getArtist(Long id) {
        return restTemplate.getForObject(ARTIST_SERVICE_URL + "/" + id, ArtistDTO.class);
    }

//    public List<ArtistDTO> getAllArtists() {
//        ArtistDTO[] artists = restTemplate.getForObject("http://localhost:8081/public/artists", ArtistDTO[].class);
//        return Arrays.asList(artists);
//    }


}


