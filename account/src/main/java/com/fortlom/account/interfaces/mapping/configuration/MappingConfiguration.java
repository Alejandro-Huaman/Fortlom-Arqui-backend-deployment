package com.fortlom.account.interfaces.mapping.configuration;
import com.fortlom.account.interfaces.mapping.entity.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("enhancedModelMapperConfiguration")
public class MappingConfiguration {
    @Bean
    public EnhancedModelMapper modelMapper() {
        return new EnhancedModelMapper();
    }
    @Bean
    public ArtistMapper artistMapper() {
        return new ArtistMapper();
    }
    @Bean
    public FanaticMapper fanaticMapper() {
        return new FanaticMapper();
    }
    @Bean
    public TagMapper tagMapper() {
        return new TagMapper();
    }

    @Bean
    public UserAccountMapper UserAccountMapper() {
        return new UserAccountMapper();
    }

    @Bean
    public AlbumMapper AlbumMapper() {
        return new AlbumMapper();
    }

    @Bean
    public SongMapper SongMapper() {
        return new SongMapper();
    }
}
